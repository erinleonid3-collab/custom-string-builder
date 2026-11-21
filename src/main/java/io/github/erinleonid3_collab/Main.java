package io.github.erinleonid3_collab;


public class Main {
    public static void main(String[] args) {
        CustomStringBuilder sb = new CustomStringBuilder();
        sb.append("Hello");
        sb.append(", ");
        sb.append("World!");
        System.out.println(sb);
        System.out.println("Длинна: "+ sb.length());
        System.out.println("Емкость: "+ sb.capacity());
        sb.undo();
        System.out.println("После отката через undo: " + sb);
        sb.insert(5, " abc ");
        System.out.println("После insert: "+ sb);
        sb.delete(5, 11);
        System.out.println("После delete: " + sb);
        System.out.println("Символ по индексу charAt(1): "+sb.charAt(1));
        System.out.println("Вхождение подстроки indexOf: "+ sb.indexOf("e"));
    }
}