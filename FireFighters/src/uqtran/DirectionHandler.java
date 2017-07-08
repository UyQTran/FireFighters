/**
 * Created by uqton on 11.06.2017.
 */

package uqtran;

import java.util.ArrayList;
import java.util.Arrays;

public class DirectionHandler {

    public static void init() {
        ArrayList<Direction> directionList = new ArrayList<>( Arrays.asList( Direction.values() ) );
        int maxIndex = directionList.size()-1;

        for( int i = 0; i < maxIndex+1; i++ ) {
            Direction current = directionList.get( i );
            if( i < maxIndex - 1 ) {
                current.next = directionList.get( i + 1 );
            } else {
                current.next = directionList.get( 0 );
            }

            if( i > 0 ) {
                current.prev = directionList.get( i - 1 );
            } else {
                current.prev = directionList.get( maxIndex );
            }
        }
    }
}
