public class TextBlockSource {
    public static void main(String[] args) {
        String sql = "SELECT" +
                "    amount" +
                "FROM" +
                "    table";
        System.out.println(sql);

        String sql2 = "SELECT" +
                sql +
                "FROM" +
                "    table";
    }
}
