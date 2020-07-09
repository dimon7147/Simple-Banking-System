package banking;

public class Main {
    public static void main(String[] args) {
        var sql = new SqlLiteDB("jdbc:sqlite:" + args[1]);
        var bank = new Bank(sql);
        bank.init();
    }
}
