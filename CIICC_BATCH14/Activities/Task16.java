package Activities;


class Gorilla implements Animal {

    @Override
    public boolean feed(boolean timeToEat) {
        // put gorilla food into cage
        return timeToEat;
    }

    @Override
    public void groom() {
        // lather, rinse, repeat
    }

    @Override
    public void pet() {
        // pet at your own risk
    }
}
