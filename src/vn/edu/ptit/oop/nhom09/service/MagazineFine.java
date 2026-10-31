package vn.edu.ptit.oop.nhom09.service;

public class MagazineFine implements FineCalculator {

    private static final double FINE_PER_DAY = 1000;

    @Override
    public double calculateFine(long overdueDays) {
        if (overdueDays <= 0) {
            return 0;
        }

        return overdueDays * FINE_PER_DAY;
    }
}
