public class ArrayCalc {

    public void checkSize(String[][] array){
        checkSize(array.length);

        for (String[] strings : array) {
            checkSize(strings.length);
        }
    }

    private void checkSize(int length){
        if (length != 4)
            throw new MyArraySizeException("Неверный размер массива");
    }

    public int calc(String[][] array){
        checkSize(array);
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                try{
                    sum += Integer.parseInt(array[i][j]);
                } catch (NumberFormatException e){
                    String message = String.format("Элемент[%s][%s] содержит не число", (i + 1), (j + 1));
                    throw new MyArrayDataException(message, e);
                }
            }
        }

        return sum;
    }

}
