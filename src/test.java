public class test{


    public static void main(String[] args) {

        String[]  string = {"1","2"};

        byte[] a = {1,2,3,4};
        int i=0;
        while(i<a.length) {

            System.out.println(a[i]);
            i++;
        }

        for(byte b:a){
            System.out.println(b);
        }

        for (String str : string){
            System.out.println(toBinary(str));
        }

//        System.out.println(Integer.parseInt("1100001",2));
    }

    //字符的二进制就是，在对应编码格式的位置，比如unicode a shi 97
    public static String  toBinary(String str){
        char[] strChar=str.toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
            if (i <(strChar.length-1)) {
                result += Integer.toBinaryString(strChar[i]) + " ";
            }
            else {
                result += Integer.toBinaryString(strChar[i]);
            }
        }
        System.out.println(Integer.parseInt(result,2));
        return result;
    }


}
