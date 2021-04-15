package modele;

import java.util.ArrayList;
import java.util.List;

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
            frottement = 0.2;
        }
        else if (type == 2) {/*beton*/
            traversable = false;
            frottement = 0.9;
        }
        else if (type == 3) {/*bois*/
            traversable = false;
            frottement = 0.5;
        }
        else if (type == 4) {/*gazon*/
            traversable = false;
            frottement = 2;
        }
        else if (type == 5) {/*trou*/
            traversable = false;
            frottement = 0;
        }
    }

    public boolean isTraversable() { return traversable; }

    public double getFrottement() { return frottement; }

}
