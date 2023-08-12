package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Выбери содержимое файлов, где \"i\" - числа, \"s\" - строки");
        String fileContent = bufferedReader.readLine();
        switch (fileContent) {
            case "i" -> {
                List<String> filesName = new ArrayList<>();
                String fileName;
                System.out.println("Введите колличество читаемых файлов (но не больше 10)");
                int amount = Integer.parseInt(bufferedReader.readLine());
                for (int i = 1; i <= amount; i++) {
                    System.out.println("Введи имя файла №" + i);
                    fileName = bufferedReader.readLine();
                    filesName.add(fileName);
                }

                correctSortInt(validFilesForReadWithInt(filesName));
                System.out.println("Введите имя файла для записи");
                String fileNameForWrite = bufferedReader.readLine();
                Files.createFile(Paths.get(fileNameForWrite));
                List<List<Integer>> fileContentInt = validFilesForReadWithInt(filesName);

                List<Integer> resultList = listConcatenationInt(fileContentInt);
                int low = 0;
                int high = resultList.size()-1;
                sortListInt(resultList, low, high);
                List<String> list = resultList.stream().map(integer -> Integer.toString(integer)).toList();
                Files.write(Paths.get(fileNameForWrite), list, StandardOpenOption.WRITE);
                System.out.println("Все готово");
            }

            case "s" -> {
                List<String> filesName = new ArrayList<>();
                String fileName;
                System.out.println("Введите колличество читаемых файлов (но не больше 10)");
                int amount = Integer.parseInt(bufferedReader.readLine());
                for (int i = 1; i <= amount; i++) {
                    System.out.println("Введи имя файла №" + i);
                    fileName = bufferedReader.readLine();
                    filesName.add(fileName);
                }
                correctSortStr(validFilesForReadWithStr(filesName));
                System.out.println("Введите имя файла для записи");
                String fileNameForWrite = bufferedReader.readLine();
                Path testFile1 = Files.createFile(Paths.get(fileNameForWrite));
                List<List<String>> fileContentStr = validFilesForReadWithStr(filesName);

                List<String> resultList = listConcatenationStr(fileContentStr);
                int low = 0;
                int high = resultList.size()-1;
                sortListStr(resultList, low, high);
                Files.write(Paths.get(fileNameForWrite), resultList, StandardOpenOption.WRITE);
                System.out.println("Все готово");
            }
        }
    }

    public static List<Integer> listConcatenationInt(List<List<Integer>> map) {
        List<Integer> resultList = new LinkedList<>();
        for (int i = 0; i < map.size(); i++) {
                resultList.addAll(map.get(i));
        }
        return resultList;
    }

    public static void sortListInt(List<Integer> list, int low, int high) {
        if (list.size() == 0)
            return;

        if (low >= high)
            return;

        int middle = low + (high - low) / 2;
        int opora = list.get(middle);

        int i = low, j = high;
        while (i <= j) {
            while (list.get(i) < opora) {
                i++;
            }

            while (list.get(j) > opora) {
                j--;
            }

            if (i <= j) {
                Collections.swap(list, i, j);
                i++;
                j--;
            }
        }
        if (low < j)
            sortListInt(list, low, j);

        if (high > i)
            sortListInt(list, i, high);
    }

    public static List<String> listConcatenationStr(List<List<String>> map) {
        List<String> resultList = new LinkedList<>();
        for (int i = 0; i < map.size(); i++) {
            resultList.addAll(map.get(i));
        }
        return resultList;
    }

    public static void sortListStr(List<String> list, int low, int high) {
        if (list.size() == 0)
            return;

        if (low >= high)
            return;

        int middle = low + (high - low) / 2;
        int opora = list.get(middle).length();

        int i = low, j = high;
        while (i <= j) {
            while (list.get(i).length() < opora) {
                i++;
            }

            while (list.get(j).length() > opora) {
                j--;
            }

            if (i <= j) {
                Collections.swap(list, i, j);
                i++;
                j--;
            }
        }
        if (low < j)
            sortListStr(list, low, j);

        if (high > i)
            sortListStr(list, i, high);
    }

    public static void correctSortInt(List<List<Integer>> map) {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                if (j+1>=map.get(i).size()) {
                    break;
                }
                if (map.get(i).get(j) > (map.get(i).get(j+1))) {
                    throw new RuntimeException("The file must be sorted");
                }

            }
        }
    }

    public static void correctSortStr(List<List<String>> map) {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                if (j+1>=map.get(i).size()) {
                    break;
                }
                if (map.get(i).get(j).length() > (map.get(i).get(j+1)).length()) {
                    throw new RuntimeException("The file must be sorted");
                }

            }
        }
    }

    public static List<List<Integer>> validFilesForReadWithInt(List<String> filesName) {
        if (filesName.size() > 10) {
            throw new RuntimeException("The number of files cannot exceed 10");
        }
        List<List<Integer>> fileContent = new ArrayList<>();
        for (String str:filesName) {
            Path path = Paths.get(str);
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("File with name " + str + " not found");
            } else {
                List<String> lines = null;
                try {
                    lines = Files.readAllLines(path, UTF_8);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                List<Integer> integerList = new ArrayList<>();
                for (String s : lines) {
                    integerList.add(Integer.parseInt(s));
                }
                fileContent.add(integerList);
            }
        }
        return fileContent;
    }

    public static List<List<String>> validFilesForReadWithStr(List<String> filesName)  {
        if (filesName.size() > 10) {
            throw new RuntimeException("The number of files cannot exceed 10");
        }
        List<List<String>> fileContent = new ArrayList<>();
        for (String str:filesName) {
            Path path = Paths.get(str);
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("File with name " + str + " not found");
            } else {
                List<String> lines = null;
                try {
                    lines = Files.readAllLines(path, UTF_8);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fileContent.add(lines);
            }
        }
        return fileContent;
    }
}