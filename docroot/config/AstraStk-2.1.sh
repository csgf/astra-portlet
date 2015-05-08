#####################################################################
# Script        : AstraStk.sh (bash)                                #
# Release       : 2.1                                               #
# Last Update   : January 14, 2012                                  #
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
MIN=$1
MAX=$2
MINVELOCITY=$3
MAXVELOCITY=$4
STEPVELOCITY=$5
DEMO2RUN=`echo $6 | awk -F'.' '{print $1}'`

ARCH=`uname -i`
if [ "X${ARCH}" = "Xx86_64" ] ; then
        export LD_LIBRARY_PATH=${VO_EUMED_SW_DIR}/ASTRA-2.0/usr/lib64/:${LD_LIBRARY_PATH}
else
        export LD_LIBRARY_PATH=${VO_EUMED_SW_DIR}/ASTRA-2.0/usr/lib/:${LD_LIBRARY_PATH}
fi

# Set other environment variable.
LOCALDIR=`pwd`
#echo $LD_LIBRARY_PATH
chmod +x ${LOCALDIR}/generatetone.sh

echo;echo "Running \"ASTRA-STK\" at "`hostname -f`
echo "Unpacking the archive  [ ${LOCALDIR}/AstraStk.tar.gz ]"
tar zxf ${LOCALDIR}/AstraStk.tar.gz
echo "Copying the input file [ $6 ]"
mv $6 ${LOCALDIR}/AstraStk
cd ${LOCALDIR}/AstraStk 
echo "Building.."
make

if [ $? -eq 0 ] ; then
echo ./stktest bachfugue.ski ${DEMO2RUN}.wav
./stktest bachfugue.ski ${DEMO2RUN}.wav

if [ -e ${LOCALDIR}/AstraStk/${DEMO2RUN}.wav ] ; then
	mv ${LOCALDIR}/AstraStk/${DEMO2RUN}.wav ${LOCALDIR}
  	echo "Audio file successfully created."

	# Start samples generation.
	echo;echo -n "Start generating sound samples at: ";date

	for (( i=$MIN ; i<=$MAX ; i++ )); do
	  echo "Generating sample: $i"
	   for (( j=$MINVELOCITY ; j<=$MAXVELOCITY ; j=j+STEPVELOCITY ))
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

else
	echo "Some problem(s) occured during the creation of the ${DEMO2RUN}.wav file.";echo
	ls -al ${LOCALDIR}/AstraStk
fi

echo -n "..Finish at ";date
fi
