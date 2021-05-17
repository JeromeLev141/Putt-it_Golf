package modele;

public class Sol {

    private boolean traversable;
    private double frottement;

    public Sol(Boolean traversable, double frottement) {
        this.traversable = traversable;
        this.frottement = frottement;
    }

    public Sol(int type){
        if (type == 1) {/*eau*/
            traversable = true;
            frottement = 0;
        }
        else if (type == 2) {/*glace*/
            traversable = false;
            frottement = 0.1;
        }
        else if (type == 3) {/*boost*/
            traversable = false;
            frottement = -5;
        }
        else if (type == 4) {/*gazon*/
            traversable = false;
            frottement = 2;
        }
        else if (type == 5) {/*trou*/
            traversable = false;
            frottement = -1;
        }
        else if (type == 6){//reset
            traversable = false;
            frottement = -2;
        }
    }

    public boolean isTraversable() { return traversable; }

    public double getFrottement() { return frottement; }

    @Override
    public String toString() {
        return "Sol{" +
                "traversable=" + traversable +
                ", frottement=" + frottement +
                '}';
    }
}
