package com.gk.indus.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
    public static void main(String[] args) throws Exception {
        String lineToRemove = "9000128";
        //removeLine2(lineToRemove);
       // String str = "9000" + countLine("c:/tmp/11.txt");
        //appendText("c:/tmp/11.txt", str);

        removeFirstLine("c:/tmp/1.txt");
    }

    public void removeLine(String lineContent) throws IOException
    {
        File file = new File("c:/tmp/1.txt");
        List<String> out = Files.lines(file.toPath())
                .filter(line -> !line.startsWith(lineContent))
                .collect(Collectors.toList());
        Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static void removeLine2(String lineContent) throws IOException
    {
        File file = new File("c:/tmp/1.txt");
        File temp = new File("c:/tmp/11.txt");
        PrintWriter out = new PrintWriter(new FileWriter(temp));
        Files.lines(file.toPath())
                .filter(line -> !line.contains(lineContent))
                .forEach(out::println);
        out.flush();
        out.close();
       // file.delete();
        temp.renameTo(file);
    }

    public void removeLine4(String lineContent) throws IOException {
        File inputFile = new File("myFile.txt");
        File tempFile = new File("myTempFile.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToRemove = "bbb";
        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if (trimmedLine.equals(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        boolean successful = tempFile.renameTo(inputFile);
    }

    public static int countLine(String fileName) throws IOException {
        int lines = -1;
        LineNumberReader lineNumberReader = null;
        try {
            File file = new File(fileName);
            lineNumberReader = new LineNumberReader(new FileReader(file));
            lineNumberReader.skip(Long.MAX_VALUE);
            lines = lineNumberReader.getLineNumber();
            System.out.print(lines);
        } finally {
            if (lineNumberReader != null){
                lineNumberReader.close();
            }
        }

        return lines;
    }

    public static void appendText(String fileName, String line){
        File f = new File(fileName);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
            bw.append(line);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeFirstLine(String fileName) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        //Initial write position
        long writePosition = raf.getFilePointer();
        raf.readLine();
        // Shift the next lines upwards.
        long readPosition = raf.getFilePointer();

        byte[] buff = new byte[1024];
        int n;
        while (-1 != (n = raf.read(buff))) {
            raf.seek(writePosition);
            raf.write(buff, 0, n);
            readPosition += n;
            writePosition += n;
            raf.seek(readPosition);
        }
        raf.setLength(writePosition);
        raf.close();
    }

}

class IOCopier {
    public static void joinFiles(File destination, File[] sources)
            throws IOException {
        OutputStream output = null;
        try {
            output = createAppendableStream(destination);
            for (File source : sources) {
                appendFile(output, source);
                output.write("\n".getBytes());
                output.flush();
            }
        } finally {
            IOUtils.closeQuietly(output);
        }
    }

    private static BufferedOutputStream createAppendableStream(File destination)
            throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(destination, true));
    }

    private static void appendFile(OutputStream output, File source)
            throws IOException {
        InputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(source));
            IOUtils.copy(input, output);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }
}

class IOUtils {
    private static final int BUFFER_SIZE = 1024 * 4;

    public static long copy(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static void closeQuietly(Closeable output) {
        try {
            if (output != null) {
                output.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
