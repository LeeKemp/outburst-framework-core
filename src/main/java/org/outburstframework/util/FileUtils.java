/*
 * Copyright 2009 - 2010 Lee Kemp
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.outburstframework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileUtils
{
	private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * Will return a files contents as a byte array or 
	 * null if the file could not be found or opened. 
	 * 
	 * @param path
     *          The path to the file to load and get bytes from
	 * @return
     *          Byte array containing the contents of the files at the specified path or null if the file cannot be found
	 */
	public static byte[] loadFile(String path)
	{

		FileInputStream fStream  = null;
		try
		{
			File f = new File(path);
			if(!f.exists())
				throw new FileNotFoundException(path);
			
			byte[] fileBytes = new byte[(int) f.length()];
			fStream = new FileInputStream(f);
			fStream.read(fileBytes, 0, (int) f.length());

			return fileBytes;
		}
		catch(FileNotFoundException e)
		{
			log.debug("file not found =" + path, e);
		}
		catch(IOException e)
		{
			log.debug("file was =" + path, e);
		}
		finally 
		{
			try {
				if(fStream!=null)
				{
					fStream.close();
				}
			}
			catch(IOException e)
			{
				log.error(e.getMessage(), e);
			}
		}

		return null;
	}
	
	public static boolean fileExists(String path)
	{
		File file = new File(path);
		return file.exists();
	}

	/**
	 * Creates a new blank file if one does not already exist. 
	 * 
	 * @param path
	 * 		The path and name of the file to create.
	 * 
	 * @return
	 * 		If the file was created
     *
     * @throws IOException
     *         If there is a problem creating the file
	 */
	public static boolean createFile(String path) throws IOException
	{
		File file = new File(path);
		return file.createNewFile();
	}
	
	public static List<File> getFiles(String path)
	{
		File pathFile = new File(path);
		File[] files = pathFile.listFiles();
		List<File> fileList = new ArrayList<File>();
		
		if(files != null)
		{

            fileList.addAll(Arrays.asList(files));
		}
		
		return fileList;		
	}
	
	public static List<File> orderFilesByLastModified(List<File> files)
	{
		Collections.sort( files, new Comparator<File>()
	    {
			public int compare(File arg0, File arg1)
			{
				long value = arg0.lastModified() - arg1.lastModified(); 
				return (int) value;
			}
	    });
		
		return files;
	}
	
}
