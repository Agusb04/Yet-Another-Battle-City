package ssj.modelos.LogicaMovimiento;

public enum Direccion {
    ARRIBA(0, -1, 270),
    ABAJO(0, 1, 90),
    IZQUIERDA(-1, 0, 180),
    DERECHA(1, 0, 0);

    private final int dx;
    private final int dy;
    private final int angulo; // grados para rotación del sprite

    Direccion(int dx, int dy, int angulo) {
        this.dx = dx;
        this.dy = dy;
        this.angulo = angulo;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public int getAngulo() {
        return angulo;
    }
}
