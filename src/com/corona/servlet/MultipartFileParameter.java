/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.server.UID;

/**
 * <p>This parameter is used to store upload file to temporary file </p>
 *
 * @author $Author$
 * @version $Id$
 */
class MultipartFileParameter extends MultipartParameter {

	/**
	 * the file name
	 */
	private String filename;

	/**
	 * the content type
	 */
	private String contentType;

	/**
	 * the file size
	 */
	private int fileSize;

	/**
	 * the output stream as byte array
	 */
	private ByteArrayOutputStream byteOutput = null;

	/**
	 * the output stream as file
	 */
	private FileOutputStream fileOutput = null;

	/**
	 * the temporary file
	 */
	private File tempFile = null;

	/**
	 * @param name the parameter name
	 */
	public MultipartFileParameter(final String name) {
		super(name);
	}

	/**
	 * @return the file name
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the file name
	 */
	public void setFilename(final String filename) {
		this.filename = filename;
	}

	/**
	 * @return the content type
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the content type
	 */
	public void setContentType(final String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the file size
	 */
	public int getFileSize() {
		return fileSize;
	}

	/**
	 * create temporary file
	 */
	public void createTempFile() {
		
		try {
			tempFile = File.createTempFile(new UID().toString().replace(":", "-"), ".upload");
			tempFile.deleteOnExit();
			fileOutput = new FileOutputStream(tempFile);
		} catch (IOException ex) {
			throw new FileUploadException("Could not create temporary file");
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.MultipartParameter#appendData(byte[], int, int)
	 */
	@Override
	public void appendData(final byte[] data, final int start, final int length) throws IOException {
		
		if (fileOutput != null) {
			fileOutput.write(data, start, length);
			fileOutput.flush();
		} else {
			if (byteOutput == null) {
				byteOutput = new ByteArrayOutputStream();
			}
			
			byteOutput.write(data, start, length);
		}

		fileSize += length;
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		
		// close file output stream if not closed
		if (fileOutput != null) {
			try {
				fileOutput.close();
			} catch (IOException e) {
			}
			fileOutput = null;
		}

		if (byteOutput != null) {
			return byteOutput.toByteArray();
		} else if (tempFile != null) {
			if (tempFile.exists()) {
				try {
					FileInputStream fIn = new FileInputStream(tempFile);
					ByteArrayOutputStream bOut = new ByteArrayOutputStream();
					byte[] buf = new byte[512];
					int read = fIn.read(buf);
					while (read != -1) {
						bOut.write(buf, 0, read);
						read = fIn.read(buf);
					}
					bOut.flush();

					fIn.close();
					tempFile.delete();
					return bOut.toByteArray();
				} catch (IOException ex) { /* too bad? */
				}
			}
		}

		return null;
	}

	/**
	 * @return the input stream
	 */
	public InputStream getInputStream() {
		
		if (fileOutput != null) {
			try {
				fileOutput.close();
			} catch (IOException ex) {
			}
			fileOutput = null;
		}

		if (byteOutput != null) {
			return new ByteArrayInputStream(byteOutput.toByteArray());
		} else if (tempFile != null) {
			try {
				return new FileInputStream(tempFile) {

					@Override
					public void close() throws IOException {
						super.close();
						tempFile.delete();
					}
				};
			} catch (FileNotFoundException ex) {
			}
		}

		return null;
	}
}
