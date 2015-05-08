#!/bin/bash
min=35
max=84
minvelocity=120
maxvelocity=120
stepvelocity=10

for(( i=$min ; i<=$max ; i++ ))
do
  echo "generating sample: $i"
   for(( j=$minvelocity ; j<=$maxvelocity ; j=j+stepvelocity  ))
   do
     echo -e "- velocity=$j "
    ./generatetone.sh $i $j
    ../stktest ${i}_${j}.ski ${i}_${j}.wav
     echo -e "--> Done \n"
   done   
done


