// This class provides immutable Point (x,y) to use in HashMap

public final class Square {
    final int x, y;

    public Square(int x, int y){
        this.x=x;
        this.y=y;
    }

    //Ref1: https://www.geeksforgeeks.org/how-to-create-a-java-hashmap-of-user-defined-class-type/
    //Ref2: https://stackoverflow.com/questions/892618/create-a-hashcode-of-two-numbers

    // Overriding the hashcode() function
    @Override
    public int hashCode()
    {

        // uses roll no to verify the uniqueness
        // of the object of Square class

        final int prime1 = 23;
        final int prime2 = 31;

        return (prime1*this.x+prime2*this.y);
    }

    // Equal objects must produce the same
    // hash code as long as they are equal
    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Square other = (Square)o;
        if ((this.x != other.x)||(this.y != other.y)) {
            return false;
        }
        return true;
    }

}
