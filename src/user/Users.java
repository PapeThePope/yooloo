package user;

import java.io.*;
import java.util.HashMap;

public class Users {

    private static final String FILE_PATH = "./players.txt";

    public static User getUser( String name ) {
        HashMap<String, User> cached_users = getUsers();

        if( cached_users.containsKey( name ) ) {
            return cached_users.get( name );
        }

        return new User( name );
    }

    public static void updateUser( User user ) {
        HashMap<String, User> cached_users = getUsers();

        cached_users.put( user.getName(), user);

        setUsers( cached_users );
    }

    private static HashMap<String, User> getUsers() {
        try {
            File file = new File( FILE_PATH );
            if( file.isFile() && file.length() > 0 ) {
                FileInputStream file_stream = new FileInputStream( file );
                ObjectInputStream object_stream = new ObjectInputStream( file_stream );

                HashMap<String, User> cached_users = ( HashMap<String, User> ) object_stream.readObject();

                object_stream.close();
                file_stream.close();

                return cached_users;
            }
        } catch ( IOException | ClassNotFoundException e ) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    private static void setUsers( HashMap<String, User> cache ) {
        try {
            File file = new File( FILE_PATH );
            if( !file.exists() ) {
                file.createNewFile();
            }
            FileOutputStream file_stream = new FileOutputStream( file );
            ObjectOutputStream object_stream = new ObjectOutputStream( file_stream );

            object_stream.writeObject( cache );
            object_stream.flush();
            object_stream.close();
            file_stream.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

}
