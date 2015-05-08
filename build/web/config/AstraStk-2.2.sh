#####################################################################
# Script        : AstraStk.sh (bash)                                #
# Release       : 2.2                                               #
# Last Update   : September 05, 2012                                #
# ================================================================  #
# Author        : Domenico Vicinanza (domenico.vicinanza@dante.org) #
# Organization  : DANTE				                    #
# Address       : City House, 126-130 Hills Rd			    #
#		  Cambridge - CB2 1PQ                               #
# Phone         : (+44) 1223 371300                                 #
# ================================================================  #
# Author        : Giuseppe La Rocca (giuseppe.larocca@ct.infn.it)   #
# Organization  : Physics Institute for Nuclear Research            #
#               : INFN - Univ. of Catania                           #
# Address       : Via S. Sofia, 64                                  #
#               : 95123 Catania (CT) - ITALY                        #
# Phone         : (+39) 095.378.55.19                               #
#####################################################################

#!/bin/sh
# ++ Configure input setting(s) for ASTRA-STK-2.2 ++
MIN=$1
MAX=$2
MINVELOCITY=$3
MAXVELOCITY=$4
STEPVELOCITY=$5
DEMO2RUN=`echo $6 | awk -F'.' '{print $1}'`
MIDDLEWARE=$7

if [ "X${MIDDLEWARE}" = "Xglite" ] ; then
	export VO_NAME=$(voms-proxy-info -vo)
	export VO_VARNAME=$(echo $VO_NAME | sed s/"\."/"_"/g | sed s/"-"/"_"/g | awk '{ print toupper($1) }')
	export VO_SWPATH_NAME="VO_"$VO_VARNAME"_SW_DIR"
	export VO_SWPATH_CONTENT=$(echo $VO_SWPATH_NAME | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')

	echo;echo "- The selected middleware is "$7
	echo "[ Settings for the gLite infrastructure ]"
	echo "VO_NAME          : "$VO_NAME
	echo "VO_VARNAME       : "$VO_VARNAME
	echo "VO_SWPATH_NAME   : "$VO_SWPATH_NAME
	echo "VO_SWPATH_CONTENT: "$VO_SWPATH_CONTENT

	echo;echo "- Checking for g++ compiler..."
	rpm -qa | grep -i gcc-c++

	echo;echo "- Checking for ASTRA-2.0 software..."
	echo tree -L 3 $VO_SWPATH_CONTENT/ASTRA-2.0
	tree -L 3 $VO_SWPATH_CONTENT/ASTRA-2.0
fi

if [ "X${MIDDLEWARE}" = "Xwsgram" ] ; then
	ASTRA_INSTALL_DIR="/usr/local/GARUDA/apps/AstraStk"
	echo;echo "- The selected middleware is "$7

	echo;echo "- Checking for ASTRA-2.0 software..."
        echo tree -L 3 $ASTRA_INSTALL_DIR/ASTRA-2.0
        tree -L 3 $ASTRA_INSTALL_DIR/ASTRA-2.0
fi

echo;echo "[ ASTRA input settings ]"
echo "MIN          : "$1
echo "MAX          : "$2
echo "MINVELOCITY  : "$3
echo "MAXVELOCITY  : "$4
echo "STEPVELOCITY : "$5
echo "DEMO2RUN     : "$6
echo "ARCHITECTURE : "`uname -i`
echo

ARCH=`uname -i`
if [ "X${ARCH}" = "Xx86_64" ] ; then
	if [ "X${MIDDLEWARE}" = "Xglite" ] ; then
        	export LD_LIBRARY_PATH=${VO_SWPATH_CONTENT}/ASTRA-2.0/usr/lib64/:${LD_LIBRARY_PATH}
	fi

	if [ "X${MIDDLEWARE}" = "Xwsgram" ] ; then
		export LD_LIBRARY_PATH=/lib64/:${LD_LIBRARY_PATH}
	fi
else
	if [ "X${MIDDLEWARE}" = "Xglite" ] ; then
        	export LD_LIBRARY_PATH=${VO_SWPATH_CONTENT}/ASTRA-2.0/usr/lib/:${LD_LIBRARY_PATH}
	fi

	if [ "X${MIDDLEWARE}" = "Xwsgram" ] ; then
		export LD_LIBRARY_PATH=/lib/:${LD_LIBRARY_PATH} 
	fi
fi

cat <<EOF >> output.README
#
# README - ASTRA
#
# Giuseppe LA ROCCA, INFN Catania
# <mailto:giuseppe.larocca@ct.infn.it>
#

Instructions for users:
~ This portlet implements the sound/timbre reconstruction of ancient instruments 
  (not existing anymore) using archaeological data as fragments from excavations, 
  written descriptions, pottery, pictures, ...

  The technique used is the Physical Modeling Synthesis, a complex digital audio 
  rendering technique which allows modeling the time-domain physics of the instrument.

  For further information, please refer to the URL http://www.astraproject.org/

If the job has been successfully executed, the following files will be produce:
~ AstraStk.out:   the standard output file;
~ AstraStk.err:   the standard error file;
~ <demo>.wav:     a MIDI file about an opera played using the Epigonion;
~ samples.tar.gz: containing the sound of each singular string of the Epigonion.
EOF

# Set other environment variable.
LOCALDIR=`pwd`
echo "- The libraries used for BUILDING are the following: " $LD_LIBRARY_PATH
chmod +x ${LOCALDIR}/generatetone.sh

echo;echo "- Running \"ASTRA-STK-2.2\" at "`hostname -f`
echo "- Unpacking the archive  [ ${LOCALDIR}/AstraStk.tar.gz ]"
tar zxf ${LOCALDIR}/AstraStk.tar.gz 2>/dev/null
echo "- Copying the input file [ $6 ]"
mv $6 ${LOCALDIR}/AstraStk
cd ${LOCALDIR}/AstraStk
echo "- Configuring the Makefile for the current infrastructure..."

if [ "X${MIDDLEWARE}" = "Xglite" ] ; then
cat ./Makefile | sed 's/PATH_TO_CHANGE/${VO_SWPATH_CONTENT}/g' > Makefile_new
fi

if [ "X${MIDDLEWARE}" = "Xwsgram" ] ; then
export ASTRA_INSTALL_DIR="/usr/local/GARUDA/apps/AstraStk"
cat ./Makefile | sed 's/PATH_TO_CHANGE/${ASTRA_INSTALL_DIR}/g' > Makefile_new
fi

mv Makefile_new Makefile 2>/dev/null

echo; echo "- Building in progress..."
make

if [ "X${MIDDLEWARE}" = "Xglite" ] ; then
	echo ./stktest bachfugue.ski ${DEMO2RUN}.wav
	./stktest bachfugue.ski ${DEMO2RUN}.wav
fi

if [ "X${MIDDLEWARE}" = "Xwsgram" ] ; then
	echo $ASTRA_INSTALL_DIR/stktest bachfugue.ski ${DEMO2RUN}.wav
	$ASTRA_INSTALL_DIR/stktest bachfugue.ski ${DEMO2RUN}.wav
fi

if [ -e ${LOCALDIR}/AstraStk/${DEMO2RUN}.wav ] ; then
        mv ${LOCALDIR}/AstraStk/${DEMO2RUN}.wav ${LOCALDIR}
        echo;echo "- Audio file successfully created!"

        # Start samples generation.
        echo;echo -n "- Start generating sound samples at: ";date

        for (( i=$MIN ; i<=$MAX ; i++ )); do
          echo "==> Generating sample: $i"
           for (( j=$MINVELOCITY ; j<=$MAXVELOCITY ; j=j+STEPVELOCITY ))
           do
             echo -e "- velocity=$j "
             ../generatetone.sh $i $j
	     if [ "X${MIDDLEWARE}" = "Xglite" ] ; then
            	./stktest ${i}_${j}.ski ${i}_${j}.wav
	     fi

	     if [ "X${MIDDLEWARE}" = "Xwsgram" ] ; then
             	$ASTRA_INSTALL_DIR/stktest ${i}_${j}.ski ${i}_${j}.wav
	     fi
	     echo -e "--> Done \n"
           done
        done

        cd ${LOCALDIR}/AstraStk/
        # Creation of a tar-ball with all the sample(s)
        tar zcf samples.tar.gz *.wav
        mv samples.tar.gz ../

else
        echo " - Some problems occured during the creation of the ${DEMO2RUN}.wav file.";echo
        ls -al ${LOCALDIR}/AstraStk
fi

echo -n "..Finish at ";date
