#####################################################################
# Script        : AstraStk.sh (bash)                                #
# Release       : 1.1                                               #
# Last Update   : January 14, 2008                                  #
# ================================================================  #
# Author        : Domenico Vicinanza (domenico.vicinanza@dante.org) #
# Organization  : Acoustics and Sound Synthesis                     #
#               : DMI - Univ. of Salerno                            #
# Address       : Via Ponte Don Melillo                             #
#               : 84084 Fisciano (SA) - ITALY                       #
# Phone         : (+39) 089.96.33.30                                #
# ================================================================  #
# Author        : Giuseppe La Rocca (giuseppe.larocca@ct.infn.it)   #
# Organization  : Physics Institute for Nuclear Research            #
#               : INFN - Univ. of Catania                           #
# Address       : Via S. Sofia, 64                                  #
#               : 95123 Catania (CT) - ITALY                        #
# Phone         : (+39) 095.378.55.19                               #
#####################################################################

#!/bin/sh
# ++ Configure input setting(s) for ASTRA-STK ++
MIN=min_settings
MAX=max_settings
MINVELOCITY=minvelocity_settings
MAXVELOCITY=maxvelocity_settings
STEPVELOCITY=stepvelocity_settings
# ++ Configure input setting(s) for ASTRA-STK ++

# Set other environment variable.
LOCALDIR=`pwd`
export LD_LIBRARY_PATH=${VO_GILDA_SW_DIR}/ASTRA-SLC308/usr/lib:${LD_LIBRARY_PATH}
echo $LD_LIBRARY_PATH
chmod +x ${LOCALDIR}/generatetone.sh
DEMO2RUN=FILE2RUN

echo "Running \"ASTRA-STK-SLC308\" at ";hostname -f
tar zxf ${LOCALDIR}/AstraStk.tar.gz

echo;echo "..Unpacked ${LOCALDIR}/AstraStk.tar.gz!";echo
cd ${LOCALDIR}/AstraStk 
make

echo;echo -n "Start generating sound at: ";date
echo ./stktest ${DEMO2RUN}.ski ${DEMO2RUN}.wav
./stktest ${DEMO2RUN}.ski ${DEMO2RUN}.wav

if [ -e ${LOCALDIR}/AstraStk/${DEMO2RUN}.wav ] ; then
	mv ${LOCALDIR}/AstraStk/${DEMO2RUN}.wav ${LOCALDIR}
  	echo "Output file created."
else
	echo "Some problem(s) occured during the creation of the ${DEMO2RUN}.wav file.";echo
	ls -al ${LOCALDIR}/AstraStk
fi

# Start samples generation.
echo "++ Start sample(s) generation."

for (( i=$MIN ; i<=$MAX ; i++ )); do
  echo "generating sample: $i"
   for(( j=$MINVELOCITY ; j<=$MAXVELOCITY ; j=j+STEPVELOCITY  ))
   do
     echo -e "- velocity=$j "
    ../generatetone.sh $i $j
    ./stktest ${i}_${j}.ski ${i}_${j}.wav
     echo -e "--> Done \n"
   done
done

cd ${LOCALDIR}/AstraStk/
# Creation of a tar-ball with all the sample(s)
tar zcf samples.tar.gz *.wav
mv samples.tar.gz ../

echo -n "..Finish at ";date;echo "Have a nice day!"
