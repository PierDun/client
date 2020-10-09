import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;

/**
 * Класс судуков
 * @author Земнухов Владимир
 */
class Chest implements Comparable<Chest>, Serializable {
    private String name;
    private int sum;
    private DateFormat create_format = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzzz yyyy", Locale.ENGLISH);
    private Date create_date;
    float[] state = new float[3];

    /**
     * Конструктор сундука
     *
     * @param a - задаваемое имя
     * @param b - задаваемая хранимая сумма
     */
    Chest(String a, String b, String x, String y, String z) {
        name = a;
        sum = Integer.valueOf(b);
        create_date = new Date();
        setState(Float.valueOf(x), Float.valueOf(y), Float.valueOf(z));
    }

    /**
     * Конструктор сундука
     */
    Chest() {
    }

    /**
     * Задать имя сундука
     *
     * @param name - его имя
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * Задать сумму, хранимую в сундуке
     *
     * @param sum - сумма на хранение
     */
    void setSum(String sum) {
        try {
            this.sum = Integer.parseInt(sum);
        } catch (NumberFormatException e) {
            System.out.println("Неверный числовой формат в " + this.name + "'е.");
            System.exit(1);
        }
    }

    void setCreate_date(String date){
        try {
            this.create_date = create_format.parse(date);
        } catch (ParseException e) {
            System.out.println("Строка не преобразоаывается в дату");
        }
    }

    private void setState(float x, float y, float z) {
        state[0] = x;
        state[1] = y;
        state[2] = z;
    }

    /**
     * Получить имя сундука
     *
     * @return - полученное имя
     */
    String getName() {
        return this.name;
    }

    /**
     * Узнать хранимую в сундуке сумму
     *
     * @return - хранимая сумма
     */
    int getSum() {
        return sum;
    }

    Date getCreate_date() {
        return create_date;
    }

    void printInfo(){
        System.out.printf("%s, %d, {%f, %f, %f}, %tF %tT \n", getName(), getSum(), state[0], state[1], state[2], getCreate_date(), getCreate_date());
    }

    /**
     * метод сравнения сундуков
     *
     * @param o - сундук для сравнения
     * @return - результат сравнения (1, -1, 0)
     */
    public int compareTo(Chest o) {
        try {
            if (!this.name.equals(o.name)) {
                return this.name.compareTo(o.name);
            } else {
                if (this.sum > o.sum) return 1;
                else return 0;
            }
        } catch (NullPointerException e) {
            return 0;
        }
    }
}