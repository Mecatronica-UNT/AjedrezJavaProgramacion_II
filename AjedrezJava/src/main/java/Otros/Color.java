package Otros;

public enum Color {
    BLANCO {
        @Override
        public int getDirección() {
            return -1;
        }

        @Override
        public boolean esBlanco() {
            return true;
        }

        @Override
        public boolean esNegro() {
            return false;
        }
    },
    NEGRO {
        @Override
        public int getDirección() {
            return 1;
        }

        @Override
        public boolean esBlanco() {
            return false;
        }

        @Override
        public boolean esNegro() {
            return true;
        }
    };
    
    public abstract int getDirección();
    public abstract boolean esBlanco();
    public abstract boolean esNegro();
    
}
