# gaussian-mixture.plot
# run with: gnuplot gaussian-mixture.plot

set term png large
set output "gaussian-mixture.png"

set title "Gaussian Mixture Dataset Generator"
set xlabel "x"
set ylabel "y"
plot 'gaussian-mixture.dat' with points
quit

