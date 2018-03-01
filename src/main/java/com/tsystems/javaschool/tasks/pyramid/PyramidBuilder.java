package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {


        int numbersInPyramidBase;
        int inputNumbersSize = inputNumbers.size();
        int[][] resultPyramid;
        int indent;
        int nextNumberIndex = 0;



        // Сортировка списка входных чисел

        try {
            Collections.sort(inputNumbers);
        } catch (Exception e) {
            throw new CannotBuildPyramidException();
        } catch (Error err) {
            throw new CannotBuildPyramidException();
        }


        // Проверка списка на возможность построения пирамиды

        for (int i = 1; ; i++) {
            inputNumbersSize = inputNumbersSize - i;
            if (inputNumbersSize < 0) {
                throw new CannotBuildPyramidException();
            }
            if (inputNumbersSize == 0) {
                numbersInPyramidBase = i;
                break;
            }
        }

        // Формирование пирамиды

        int matrixRows = numbersInPyramidBase;
        int matrixCols = numbersInPyramidBase * 2 - 1;

        resultPyramid = new int[matrixRows][matrixCols];
        indent = numbersInPyramidBase - 1;

        for (int i = 0; i < matrixRows; i++) {

            for (int j = 0; j < matrixCols; j++) {

                //Формирование основания пирамиды

                if (indent == 0) {
                    if (j % 2 == 0) {
                        resultPyramid[i][j] = inputNumbers.get(nextNumberIndex);
                        nextNumberIndex++;
                        continue;
                    } else {
                        resultPyramid[i][j] = 0;
                        continue;
                    }
                }

                //Формирование четных рядов

                if (indent > 0 && indent % 2 == 0) {
                    if (j >= indent && j <= matrixCols - 1 - indent && j % 2 == 0) {
                        resultPyramid[i][j] = inputNumbers.get(nextNumberIndex);
                        nextNumberIndex++;
                        continue;
                    } else {
                        resultPyramid[i][j] = 0;
                        continue;
                    }
                }

                //Формирование нечетных рядов

                if (indent > 0 && indent % 2 != 0) {
                    if (j >= indent && j <= matrixCols - 1 - indent && j % 2 != 0) {
                        resultPyramid[i][j] = inputNumbers.get(nextNumberIndex);
                        nextNumberIndex++;
                        continue;
                    } else {
                        resultPyramid[i][j] = 0;
                        continue;
                    }
                }


            }

            // Сокращаем боковой отступ для следующей строки

            indent--;

        }

        return resultPyramid;
    }
    
}
