package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.Main.listConcatenationInt;
import static org.example.Main.sortListInt;
import static org.example.Main.validFilesForReadWithInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MainTest {
    @Test
    public void testValidFilesForReadWithInt_correctAnswer() {
        List<String> filesName = List.of("C:\\JavaTest\\testFile1.txt", "C:\\JavaTest\\testFile2.txt");
        List<List<Integer>> list = validFilesForReadWithInt(filesName);
        assertEquals(2, list.size());
    }

    @Test
    public void testValidFilesForReadWithInt_nonCorrectAnswer() {
        List<String> filesName = List.of("sss", "C:\\JavaTest\\testFile2.txt");
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> validFilesForReadWithInt(filesName));
        assertEquals("File with name " + "sss" + " not found", illegalArgumentException.getMessage());
    }

    @Test
    public void testListConcatenationInt_correctAnswer() {
        List<String> filesName = List.of("C:\\JavaTest\\testFile1.txt", "C:\\JavaTest\\testFile2.txt");
        List<List<Integer>> list = validFilesForReadWithInt(filesName);
        List<Integer> resList = listConcatenationInt(list);
        List<Integer> expected = List.of(1, 2, 3, 4, 5, 3, 4, 5, 6, 7, 8);
        assertEquals(expected, resList);
    }

    @Test
    public void testSortListInt_correctAnswer() {
        List<String> filesName = List.of("C:\\JavaTest\\testFile1.txt", "C:\\JavaTest\\testFile2.txt");
        List<List<Integer>> list = validFilesForReadWithInt(filesName);
        List<Integer> resList = listConcatenationInt(list);
        sortListInt(resList, 0, resList.size()-1);
        List<Integer> expected = List.of(1, 2, 3, 3, 4, 4, 5, 5, 6, 7, 8);
        assertEquals(expected, resList);
    }

}