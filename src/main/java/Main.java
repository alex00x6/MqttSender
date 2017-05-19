import actions.Reader;
import com.beust.jcommander.JCommander;
import jar.Args;
import jar.Unpack;

/**
 * Created by Alex Storm on 19.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        String[] args1 = {"-s"};
        Unpack unpack = new Unpack();
        unpack.unpackOnStart();
        Reader reader = new Reader();
        reader.readProperties();
        Args argv = new Args();
        JCommander.newBuilder()
                .addObject(argv)
                .build()
                .parse(args1);
        argv.print();
        argv.proceed();
    }
}
