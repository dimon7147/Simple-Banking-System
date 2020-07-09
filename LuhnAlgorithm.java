package banking;

public class LuhnAlgorithm {
    public static int generateCheckNumber(String cardNumber){
        var card = cardNumber.toCharArray();
        var checkSum = 0;
        for (int i = 0; i < 15; i += 2){
            var number = Integer.parseInt(String.valueOf(card[i])) * 2;
            if (number > 9) number -= 9;
            card[i] = (char)(number + '0');
        }
        for (int i = 0; i < 15; i++){
            checkSum += Integer.parseInt(String.valueOf(card[i]));
        }
        return 10 - checkSum % 10;
    }
    public static boolean checkIfNumberCorrect(String cardNumber){
        var lastDigit = generateCheckNumber(cardNumber.substring(0,cardNumber.length()-1));
        return cardNumber.endsWith(String.valueOf(lastDigit));
    }
}
