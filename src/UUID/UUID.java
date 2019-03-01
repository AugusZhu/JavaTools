package UUID;

import java.util.UUID;

public class UuidDemo {

    public static void main(String[] args) {

        System.out.println(UUID.randomUUID().toString().replace("-", ""));
        System.out.println(UUID.randomUUID().version());
        System.out.println(UUID.nameUUIDFromBytes("890110866094329856".getBytes()).toString().replace("-", ""));
        System.out.println(UUID.nameUUIDFromBytes("890110866094329856".getBytes()).version());

    }

}