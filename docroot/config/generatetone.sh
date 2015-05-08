#!/bin/bash

note=$1
velocity=$2
header="///COM: Vicinanza Domenico \n"
header=$header"///CDT: 1568/09/03/-1634/// \n"
header=$header"///OTL: Do \n"
header=$header"///OTL2: \n"
header=$header"///ONM: \n"
header=$header"///SMS: Well tempered scale\n"
header=$header"///PPR: \n"
header=$header"///PPP: \n"
header=$header"///PDT: \n"
header=$header"///URL: \n"
header=$header"///AIN: \n"
header=$header"// Instrument <vox> for channel 1\n"
header=$header"// Tempo 120 MM per quarter note\n"
header=$header"\n"
header=$header"// Measure number 1	=0\n"
echo -e $header > ${note}_${velocity}.ski
echo "NoteOn   	0           3	  $note   $velocity" >> ${note}_${velocity}.ski
echo "NoteOff  	10           3	  $note   $velocity" >> ${note}_${velocity}.ski

footer="///ENC: \n"
footer=$footer"///END: 2008/09/11/"

echo $footer >> ${note}_${velocity}.ski

