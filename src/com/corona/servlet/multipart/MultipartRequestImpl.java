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
import java.util.regex.Matcher;
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

	/**
	 * parse multipart request
	 */
	private void parseRequest() {

		// find boundary by request content type
		byte[] boundaryMarker = getBoundaryMarker(this.request.getContentType());
		if (boundaryMarker == null) {
			throw new FileUploadException("The request was rejected because no multipart boundary was found");
		}

		// get character encoding from request 
		this.encoding = this.request.getCharacterEncoding();
		this.parameters = new HashMap<String, Parameter>();

		try {

			byte[] buffer = new byte[BUFFER_SIZE];
			Map<String, String> headers = new HashMap<String, String>();

			ReadState state = ReadState.BOUNDARY;

			InputStream input = request.getInputStream();
			int read = input.read(buffer);
			int position = 0;

			Parameter parameter = null;

			// This is a fail-safe to prevent infinite loops from occurring in some environments
			int loopCounter = 20;

			while (read > 0 && loopCounter > 0) {
				for (int i = 0; i < read; i++) {
					switch (state) {
						case BOUNDARY: 
							if (checkSequence(buffer, i, boundaryMarker) && checkSequence(buffer, i + 2, CR_LF)) {
								state = ReadState.HEADERS;
								i += 2;
								position = i + 1;
							}
							break;
						
						case HEADERS: 
							if (checkSequence(buffer, i, CR_LF)) {
								String param;
								if (encoding == null) {
									param = new String(buffer, position, i - position - 1);
								} else {
									param = new String(buffer, position, i - position - 1, encoding);
								}
								parseParameterMap(param, ";", headers);

								if (checkSequence(buffer, i + CR_LF.length, CR_LF)) {
									state = ReadState.DATA;
									i += CR_LF.length;
									position = i + 1;

									String paramName = headers.get(PARAM_NAME);
									if (paramName != null) {
										if (headers.containsKey(PARAM_FILENAME)) {
											FileParameter fp = new FileParameter(paramName);
											if (createTempFiles) {
												fp.createTempFile();
											}
											
											fp.setContentType(headers.get(PARAM_CONTENT_TYPE));
											fp.setFilename(headers.get(PARAM_FILENAME));
											parameter = fp;
										} else {
											if (parameters.containsKey(paramName)) {
												parameter = parameters.get(paramName);
											} else {
												parameter = new ValueParameter(paramName, this.encoding);
											}
										}

										if (!parameters.containsKey(paramName)) {
											parameters.put(paramName, parameter);
										}
									}

									headers.clear();
								} else {
									position = i + 1;
								}
							}
							break;
						
						default:			//case DATA: 
							// If we've encountered another boundary...
							if (checkSequence(buffer, i - boundaryMarker.length - CR_LF.length, CR_LF)
									&& checkSequence(buffer, i, boundaryMarker)) {
								// Write any data before the boundary (that hasn't already been written) to the param
								if (position < i - boundaryMarker.length - CR_LF.length - 1) {
									parameter.appendData(
											buffer, position, i - position - boundaryMarker.length - CR_LF.length - 1
									);
								}

								if (parameter instanceof ValueParameter) {
									((ValueParameter) parameter).complete();
								}

								if (checkSequence(buffer, i + CR_LF.length, CR_LF)) {
									i += CR_LF.length;
									position = i + 1;
								} else {
									position = i;
								}

								state = ReadState.HEADERS;
							} else if (i > (position + boundaryMarker.length + CHUNK_SIZE + CR_LF.length)) {
								// Otherwise write whatever data we have to the param
								parameter.appendData(buffer, position, CHUNK_SIZE);
								position += CHUNK_SIZE;
							}
							break;
						
					}
				}

				if (position < read) {
					// move the bytes that weren't read to the start of the buffer
					int bytesNotRead = read - position;
					System.arraycopy(buffer, position, buffer, 0, bytesNotRead);
					read = input.read(buffer, bytesNotRead, buffer.length - bytesNotRead);

					// Decrement loopCounter if no data was readable
					if (read == 0) {
						loopCounter--;
					}

					read += bytesNotRead;
				} else {
					read = input.read(buffer);
				}

				position = 0;
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

		if (boundary != null) {
			try {
				return boundary.getBytes("ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				return boundary.getBytes();
			}
		} else {
			return null;
		}
	}

	/**
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
	 * @param content the parameter content as string
	 * @param separator the separator
	 * @return the parameter map
	 */
	private Map<String, String> parseParameterMap(final String content, final String separator) {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		parseParameterMap(content, separator, paramMap);
		return paramMap;
	}

	/**
	 * @param content the parameter content as string
	 * @param separator the separator
	 * @param paramMap the parameter map
	 */
	private void parseParameterMap(final String content, final String separator, final Map<String, String> paramMap) {
		
		String[] parts = content.split("[" + separator + "]");
		for (String part : parts) {
			
			Matcher matcher = PARAM_VALUE_PATTERN.matcher(part);
			if (matcher.matches()) {
				
				String key = matcher.group(1);
				String value = matcher.group(2);

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
	@SuppressWarnings({ "rawtypes", "unchecked" })
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

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.multipart.MultipartRequest#getFileContentType(java.lang.String)
	 */
	public String getFileContentType(final String name) {

		Parameter parameter = getMultipartParameter(name);
		if ((parameter != null) && (parameter instanceof FileParameter)) {
			return ((FileParameter) parameter).getContentType();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.multipart.MultipartRequest#getFileContentType(java.lang.String)
	 */
	public String getFileName(final String name) {

		Parameter parameter = getMultipartParameter(name);
		if ((parameter != null) && (parameter instanceof FileParameter)) {
			return ((FileParameter) parameter).getFileName();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.multipart.MultipartRequest#getFileSize(java.lang.String)
	 */
	public int getFileSize(final String name) {

		Parameter parameter = getMultipartParameter(name);
		if ((parameter != null) && (parameter instanceof FileParameter)) {
			return ((FileParameter) parameter).getFileSize();
		} else {
			return -1;
		}
	}

	/**
	 * {@inheritDoc}
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
