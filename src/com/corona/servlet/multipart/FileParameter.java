/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.multipart;

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
class FileParameter extends Parameter {
	
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
	FileParameter(final String name) {
		super(name);
	}

	/**
	 * @return the file name
	 */
	public String getFileName() {
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
	 * @see com.corona.servlet.multiparParameterer, int, int)
	 */
	@Override
	public void appendData(final byte[] data, final int start, final int length) throws IOException {
		
		if (fileOutput != null) {
			
			// if create temporary file, write data to temporary file
			fileOutput.write(data, start, length);
			fileOutput.flush();
		} else {
			
			// if not create temporary file, just write to bytes buffer
			if (byteOutput == null) {
				byteOutput = new ByteArrayOutputStream();
			}
			
			byteOutput.write(data, start, length);
		}

		fileSize += length;
	}

	/**
	 * @return the file content as bytes
	 */
	public byte[] getData() {
		
		// close file output stream if not closed before in order to make sure it is closed
		if (fileOutput != null) {
			try {
				fileOutput.close();
			} catch (IOException e) {
				throw new FileStreamException("Fail to close temporary file output stream", e);
			}
			fileOutput = null;
		}

		// if already get data and read file to byte output, just return
		if (byteOutput != null) {
			return byteOutput.toByteArray();
		} 
		
		// if parse multipart to file and file exists, read to byte and return
		if ((tempFile != null) && tempFile.exists()) {
			
			try {
				FileInputStream input = new FileInputStream(tempFile);
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				
				byte[] buffer = new byte[512];
				for (int read = input.read(buffer); read != -1; read = input.read(buffer)) {
					output.write(buffer, 0, read);
				}
				output.flush();

				input.close();
				tempFile.delete();
				return output.toByteArray();
			} catch (IOException e) { 
				throw new FileStreamException("Fail to close temporary file input stream", e);
			}
		} 

		// if no temporary file, no bytes buffer, just return null 
		return null;
	}

	/**
	 * @return the input stream
	 */
	public InputStream getInputStream() {
		
		// close file output stream if not closed before in order to make sure it is closed
		if (fileOutput != null) {
			try {
				fileOutput.close();
			} catch (IOException e) {
				throw new FileStreamException("Fail to close temporary file output stream", e);
			}
			fileOutput = null;
		}

		// if already get data and read file to byte output, just return
		if (byteOutput != null) {
			return new ByteArrayInputStream(byteOutput.toByteArray());
		} 

		// if parse multipart to file and file exists, read to byte and return
		if ((tempFile != null) && tempFile.exists()) {
			
			try {
				return new FileInputStream(tempFile) {
					public void close() throws IOException {
						super.close();
						tempFile.delete();
					}
				};
			} catch (FileNotFoundException e) {
				throw new FileStreamException("Fail to open temporary file as input stream", e);
			}
		}

		// if no temporary file, no bytes buffer, just return null 
		return null;
	}
}
