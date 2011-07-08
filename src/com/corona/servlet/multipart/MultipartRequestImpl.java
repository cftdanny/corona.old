/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.multipart;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <p>This request is used to support upload file from client web browser. </p>
 * 
 * @author $Author$
 * @version $Id$
 */
public class MultipartRequestImpl extends HttpServletRequestWrapper implements MultipartRequest {

	/**
	 * parameter name
	 */
	private static final String PARAM_NAME = "name";

	/**
	 * file name
	 */
	private static final String PARAM_FILENAME = "filename";

	/**
	 * content type
	 */
	private static final String PARAM_CONTENT_TYPE = "Content-Type";

	/**
	 * parameter value pattern
	 */
	private static final Pattern PARAM_VALUE_PATTERN = Pattern.compile("^\\s*([^\\s=]+)\\s*[=:]\\s*(.+)\\s*$");

	/**
	 * buffer size
	 */
	private static final int BUFFER_SIZE = 2048;

	/**
	 * chunk size
	 */
	private static final int CHUNK_SIZE = 512;

	/**
	 * CR
	 */
	private static final byte CR = 0x0d;

	/**
	 * LF
	 */
	private static final byte LF = 0x0a;

	/**
	 * CR, LF
	 */
	private static final byte[] CR_LF = {CR, LF};

	/**
	 * whether create temporary file
	 */
	private boolean createTempFiles;

	/**
	 * how to encode data
	 */
	private String encoding = null;

	/**
	 * parameters
	 */
	private Map<String, Parameter> parameters = null;

	/**
	 * the parent HTTP SERVLET request
	 */
	private HttpServletRequest request;

	/**
	 * @param request the parent HTTP SERVLET request
	 * @param createTempFiles whether create temporary files
	 * @param maxRequestSize the upload file size
	 */
	public MultipartRequestImpl(final HttpServletRequest request, final boolean createTempFiles, 
			final int maxRequestSize) {

		super(request);
		
		this.request = request;
		this.createTempFiles = createTempFiles;

		String contentLength = request.getHeader("Content-Length");
		if (contentLength != null && maxRequestSize > 0 && Integer.parseInt(contentLength) > maxRequestSize) {
			throw new FileUploadException("Multipart request is larger than allowed size");
		}
	}

	private void parseRequest() {

		byte[] boundaryMarker = getBoundaryMarker(request.getContentType());
		if (boundaryMarker == null) {
			throw new FileUploadException("The request was rejected because no multipart boundary was found");
		}

		encoding = request.getCharacterEncoding();
		parameters = new HashMap<String, Parameter>();

		try {

			byte[] buffer = new byte[BUFFER_SIZE];
			Map<String, String> headers = new HashMap<String, String>();

			ReadState readState = ReadState.BOUNDARY;

			InputStream input = request.getInputStream();
			int read = input.read(buffer);
			int pos = 0;

			Parameter p = null;

			// This is a fail-safe to prevent infinite loops from occurring in some environments
			int loopCounter = 20;

			while (read > 0 && loopCounter > 0) {
				for (int i = 0; i < read; i++) {
					switch (readState) {
						case BOUNDARY: 
							if (checkSequence(buffer, i, boundaryMarker) && checkSequence(buffer, i + 2, CR_LF)) {
								readState = ReadState.HEADERS;
								i += 2;
								pos = i + 1;
							}
							break;
						
						case HEADERS: 
							if (checkSequence(buffer, i, CR_LF)) {
								String param = (encoding == null) ? new String(buffer, pos, i - pos - 1) : new String(
										buffer, pos, i - pos - 1, encoding);
								parseParameterMap(param, ";", headers);

								if (checkSequence(buffer, i + CR_LF.length, CR_LF)) {
									readState = ReadState.DATA;
									i += CR_LF.length;
									pos = i + 1;

									String paramName = headers.get(PARAM_NAME);
									if (paramName != null) {
										if (headers.containsKey(PARAM_FILENAME)) {
											FileParameter fp = new FileParameter(paramName);
											if (createTempFiles) {
												fp.createTempFile();
											}
											
											fp.setContentType(headers.get(PARAM_CONTENT_TYPE));
											fp.setFilename(headers.get(PARAM_FILENAME));
											p = fp;
										} else {
											if (parameters.containsKey(paramName)) {
												p = parameters.get(paramName);
											} else {
												p = new ValueParameter(paramName, this.encoding);
											}
										}

										if (!parameters.containsKey(paramName)) {
											parameters.put(paramName, p);
										}
									}

									headers.clear();
								} else {
									pos = i + 1;
								}
							}
							break;
						
						default:			//case DATA: 
							// If we've encountered another boundary...
							if (checkSequence(buffer, i - boundaryMarker.length - CR_LF.length, CR_LF)
									&& checkSequence(buffer, i, boundaryMarker)) {
								// Write any data before the boundary (that hasn't already been written) to the param
								if (pos < i - boundaryMarker.length - CR_LF.length - 1) {
									p.appendData(buffer, pos, i - pos - boundaryMarker.length - CR_LF.length - 1);
								}

								if (p instanceof ValueParameter) {
									((ValueParameter) p).complete();
								}

								if (checkSequence(buffer, i + CR_LF.length, CR_LF)) {
									i += CR_LF.length;
									pos = i + 1;
								} else {
									pos = i;
								}

								readState = ReadState.HEADERS;
							} else if (i > (pos + boundaryMarker.length + CHUNK_SIZE + CR_LF.length)) {
								// Otherwise write whatever data we have to the param
								p.appendData(buffer, pos, CHUNK_SIZE);
								pos += CHUNK_SIZE;
							}
							break;
						
					}
				}

				if (pos < read) {
					// move the bytes that weren't read to the start of the buffer
					int bytesNotRead = read - pos;
					System.arraycopy(buffer, pos, buffer, 0, bytesNotRead);
					read = input.read(buffer, bytesNotRead, buffer.length - bytesNotRead);

					// Decrement loopCounter if no data was readable
					if (read == 0) {
						loopCounter--;
					}

					read += bytesNotRead;
				} else {
					read = input.read(buffer);
				}

				pos = 0;
			}
		} catch (IOException ex) {
			throw new FileUploadException("IO Error parsing multipart request", ex);
		}
	}

	/**
	 * @param contentType the content type
	 * @return the boundary marker from content type
	 */
	private byte[] getBoundaryMarker(final String contentType) {

		Map<String, String> parameterMap = parseParameterMap(contentType, ";");
		String boundary = parameterMap.get("boundary");

		if (boundary == null) {
			return null;
		}

		try {
			return boundary.getBytes("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			return boundary.getBytes();
		}
	}

	/**
	 * Checks if a specified sequence of bytes ends at a specific position within a byte array.
	 * 
	 * @param data the data
	 * @param position the position
	 * @param sequence the sequence
	 * @return boolean indicating if the sequence was found at the specified position
	 */
	private boolean checkSequence(final byte[] data, final int position, final byte[] sequence) {
		
		if ((position - sequence.length < -1) || (position >= data.length)) {
			return false;
		}

		for (int i = 0; i < sequence.length; i++) {
			
			if (data[(position - sequence.length) + i + 1] != sequence[i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param paramStr the parameters as string
	 * @param separator the separator
	 * @return the parameter map
	 */
	private Map<String, String> parseParameterMap(final String paramStr, final String separator) {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		parseParameterMap(paramStr, separator, paramMap);
		return paramMap;
	}

	/**
	 * @param paramStr the parameters value as string
	 * @param separator the separator
	 * @param paramMap the parameter map
	 */
	private void parseParameterMap(final String paramStr, final String separator, final Map<String, String> paramMap) {
		
		String[] parts = paramStr.split("[" + separator + "]");
		for (String part : parts) {
			
			java.util.regex.Matcher matcher = PARAM_VALUE_PATTERN.matcher(part);
			if (matcher.matches()) {
				
				String key = matcher.group(1);
				String value = matcher.group(2);

				// Strip double quotes
				if (value.startsWith("\"") && value.endsWith("\"")) {
					value = value.substring(1, value.length() - 1);
				}

				paramMap.put(key, value);
			}
		}
	}

	/**
	 * @param name the parameter name
	 * @return the multipart parameter
	 */
	private Parameter getMultipartParameter(final String name) {
		
		if (parameters == null) {
			parseRequest();
		}
		return parameters.get(name);
	}

	/**
	 * {@inheritDoc}
	 * @see javax.servlet.ServletRequestWrapper#getParameterNames()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration getParameterNames() {
		
		if (parameters == null) {
			parseRequest();
		}

		return Collections.enumeration(parameters.keySet());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.multipart.MultipartRequest#getFileBytes(java.lang.String)
	 */
	public byte[] getFileBytes(final String name) {

		Parameter parameter = getMultipartParameter(name);
		if ((parameter != null) && (parameter instanceof FileParameter)) {
			return ((FileParameter) parameter).getData();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.multipart.MultipartRequest#getFileInputStream(java.lang.String)
	 */
	public InputStream getFileInputStream(final String name) {

		Parameter parameter = getMultipartParameter(name);
		if ((parameter != null) && (parameter instanceof FileParameter)) {
			return ((FileParameter) parameter).getInputStream();
		} else {
			return null;
		}
	}

	public String getFileContentType(String name) {
		Parameter p = getMultipartParameter(name);
		return (p != null && p instanceof FileParameter) ? ((FileParameter) p).getContentType() : null;
	}

	public String getFileName(String name) {
		Parameter p = getMultipartParameter(name);
		return (p != null && p instanceof FileParameter) ? ((FileParameter) p).getFilename() : null;
	}

	public int getFileSize(String name) {
		Parameter p = getMultipartParameter(name);
		return (p != null && p instanceof FileParameter) ? ((FileParameter) p).getFileSize() : -1;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public String getParameter(final String name) {

		Parameter parameter = this.getMultipartParameter(name);
		if ((parameter != null) && (parameter instanceof ValueParameter)) {

			// get value for value parameter that parsed from multipart request
			ValueParameter valueParameter = (ValueParameter) parameter;
			if (valueParameter.getValue() instanceof List) {
				return ((List<String>) valueParameter.getValue()).get(0);
			} else {
				return (String) valueParameter.getValue();
			}
		} else {
			
			// get value for parent request, file parameter should be null
			return super.getParameter(name);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public String[] getParameterValues(final String name) {
		
		Parameter parameter = getMultipartParameter(name);
		if ((parameter != null) && (parameter instanceof ValueParameter)) {
			
			// get values for value parameter that parsed from multipart request
			ValueParameter valueParameter = (ValueParameter) parameter;
			if (valueParameter.getValue() instanceof List) {
				List<String> values = (List<String>) valueParameter.getValue();
				return values.toArray(new String[0]);
			} else {
				return new String[] {(String) valueParameter.getValue()};
			}
		} else {
			
			// get value for parent request, file parameter should be null
			return super.getParameterValues(name);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see javax.servlet.ServletRequestWrapper#getParameterMap()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getParameterMap() {

		// if parameters for multipart is not parsed yet, will parse it first
		if (parameters == null) {
			parseRequest();
		}

		// merge parent request parameters with parsed multipart parameters and return
		Map<String, Object> parameterMap = new HashMap<String, Object>(super.getParameterMap());
		for (String name : parameters.keySet()) {
			
			Parameter p = parameters.get(name);
			if (p instanceof ValueParameter) {
				
				ValueParameter vp = (ValueParameter) p;
				if (vp.getValue() instanceof String) {
					parameterMap.put(name, vp.getValue());
				} else if (vp.getValue() instanceof List) {
					parameterMap.put(name, this.getParameterValues(name));
				}
			}
		}
		return parameterMap;
	}
}
