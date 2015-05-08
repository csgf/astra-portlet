PROGRAM=stktest

CPPFLAGS = -O3 -Wall -g -D__GXX__ -Iinclude/ -IPATH_TO_CHANGE/ASTRA-2.0/usr/include -D__LITTLE_ENDIAN__  -D__LINUX_ALSA__ -D__LINUX_ALSASEQ__

# Detect the right architecture and export LDFLAGS accordingly
ARCH:=$(shell uname -i)
ifeq ($(ARCH),x86_64)
	LDFLAGS = -LPATH_TO_CHANGE/ASTRA-2.0/usr/lib64 -lasound
else
	LDFLAGS = -LPATH_TO_CHANGE/ASTRA-2.0/usr/lib -lasound
endif

CXX = g++

OBJECTPATH=.

OUTPUTPATH=.

OBJECTS=$(OBJECTPATH)/stktest.o $(OBJECTPATH)/Delay.o $(OBJECTPATH)/DelayA.o $(OBJECTPATH)/OneZero.o $(OBJECTPATH)/OnePole.o $(OBJECTPATH)/Filter.o $(OBJECTPATH)/Noise.o $(OBJECTPATH)/Plucked.o $(OBJECTPATH)/Generator.o $(OBJECTPATH)/Instrmnt.o $(OBJECTPATH)/Messager.o  $(OBJECTPATH)/Mutex.o $(OBJECTPATH)/Thread.o $(OBJECTPATH)/TcpServer.o $(OBJECTPATH)/Socket.o $(OBJECTPATH)/RtMidi.o $(OBJECTPATH)/Skini.o $(OBJECTPATH)/WvOut.o $(OBJECTPATH)/FileWvOut.o $(OBJECTPATH)/FileWrite.o $(OBJECTPATH)/Voicer.o $(OBJECTPATH)/Stk.o  

all:	$(OUTPUTPATH)/$(PROGRAM)

$(OUTPUTPATH)/$(PROGRAM):	$(OBJECTS)
	$(CXX) -o $@ $(OBJECTS) $(LDFLAGS)

$(OBJECTPATH)/stktest.o:  stktest.cpp  
	$(CXX) -c -o $@ $(CPPFLAGS) stktest.cpp

$(OBJECTPATH)/Delay.o:  src/Delay.cpp include/Delay.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Delay.cpp

$(OBJECTPATH)/DelayA.o: src/DelayA.cpp include/DelayA.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/DelayA.cpp

$(OBJECTPATH)/Filter.o: src/Filter.cpp include/Filter.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Filter.cpp

$(OBJECTPATH)/FM.o:  src/FM.cpp include/FM.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/FM.cpp

$(OBJECTPATH)/PRCRev.o:  src/PRCRev.cpp include/PRCRev.h
	$(CXX) -c -o $@ $(CPPFLAGS) src/PRCRev.cpp

$(OBJECTPATH)/Effect.o:  src/Effect.cpp include/Effect.h
	$(CXX) -c -o $@ $(CPPFLAGS) src/Effect.cpp

$(OBJECTPATH)/ADSR.o:  src/ADSR.cpp include/ADSR.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/ADSR.cpp

$(OBJECTPATH)/Noise.o:  src/Noise.cpp/ include/Noise.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Noise.cpp

$(OBJECTPATH)/SineWave.o:  src/SineWave.cpp/ include/SineWave.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/SineWave.cpp

$(OBJECTPATH)/TwoZero.o:  src/TwoZero.cpp include/TwoZero.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/TwoZero.cpp

$(OBJECTPATH)/OneZero.o:  src/OneZero.cpp include/OneZero.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/OneZero.cpp

$(OBJECTPATH)/OnePole.o:  src/OnePole.cpp include/OnePole.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/OnePole.cpp

$(OBJECTPATH)/PoleZero.o:  src/PoleZero.cpp include/PoleZero.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/PoleZero.cpp

$(OBJECTPATH)/Envelope.o:  src/Envelope.cpp include/Envelope.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Envelope.cpp

$(OBJECTPATH)/Generator.o:  src/Generator.cpp include/Generator.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Generator.cpp

$(OBJECTPATH)/Mandolin.o:  src/Mandolin.cpp include/Mandolin.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Mandolin.cpp

$(OBJECTPATH)/Plucked.o:  src/Plucked.cpp include/Plucked.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Plucked.cpp

$(OBJECTPATH)/PluckTwo.o:  src/PluckTwo.cpp include/PluckTwo.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/PluckTwo.cpp

$(OBJECTPATH)/Bowed.o:  src/Bowed.cpp include/Bowed.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Bowed.cpp

$(OBJECTPATH)/BlowHole.o:  src/BlowHole.cpp include/BlowHole.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/BlowHole.cpp

$(OBJECTPATH)/BeeThree.o:  src/BeeThree.cpp include/BeeThree.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/BeeThree.cpp

$(OBJECTPATH)/Function.o:  src/Function.cpp include/Function.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Function.cpp

$(OBJECTPATH)/DelayL.o:  src/DelayL.cpp include/DelayL.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/DelayL.cpp

$(OBJECTPATH)/BowTable.o:  src/BowTable.cpp include/BowTable.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/BowTable.cpp

$(OBJECTPATH)/ReedTable.o:  src/ReedTable.cpp include/ReedTable.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/ReedTable.cpp

$(OBJECTPATH)/BiQuad.o:  src/BiQuad.cpp include/BiQuad.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/BiQuad.cpp

$(OBJECTPATH)/BandedWG.o:  src/BandedWG.cpp include/BandedWG.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/BandedWG.cpp

$(OBJECTPATH)/Sitar.o:  src/Sitar.cpp include/Sitar.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Sitar.cpp

$(OBJECTPATH)/Modal.o:  src/Modal.cpp include/Modal.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Modal.cpp

$(OBJECTPATH)/ModalBar.o:  src/ModalBar.cpp include/ModalBar.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/ModalBar.cpp

$(OBJECTPATH)/Instrmnt.o:  src/Instrmnt.cpp include/Instrmnt.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Instrmnt.cpp

$(OBJECTPATH)/Messager.o:  src/Messager.cpp include/Messager.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Messager.cpp

$(OBJECTPATH)/Skini.o:  src/Skini.cpp include/Skini.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Skini.cpp

$(OBJECTPATH)/Mutex.o:  src/Mutex.cpp include/Mutex.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Mutex.cpp

$(OBJECTPATH)/Thread.o:  src/Thread.cpp include/Thread.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Thread.cpp

$(OBJECTPATH)/TcpServer.o:  src/TcpServer.cpp include/TcpServer.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/TcpServer.cpp

$(OBJECTPATH)/Socket.o:  src/Socket.cpp include/Socket.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Socket.cpp

$(OBJECTPATH)/RtMidi.o:  src/RtMidi.cpp include/RtMidi.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/RtMidi.cpp

$(OBJECTPATH)/WaveLoop.o:  src/WaveLoop.cpp include/WaveLoop.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/WaveLoop.cpp

$(OBJECTPATH)/WvOut.o:  src/WvOut.cpp include/WvOut.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/WvOut.cpp

$(OBJECTPATH)/FileWvOut.o:  src/FileWvOut.cpp include/FileWvOut.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/FileWvOut.cpp

$(OBJECTPATH)/FileWrite.o:  src/FileWrite.cpp include/FileWrite.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/FileWrite.cpp

$(OBJECTPATH)/WvIn.o:  src/WvIn.cpp include/WvIn.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/WvIn.cpp

$(OBJECTPATH)/FileWvIn.o:  src/FileWvIn.cpp include/FileWvIn.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/FileWvIn.cpp

$(OBJECTPATH)/FileRead.o:  src/FileRead.cpp include/FileRead.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/FileRead.cpp

$(OBJECTPATH)/Voicer.o:  src/Voicer.cpp include/Voicer.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Voicer.cpp

$(OBJECTPATH)/Stk.o:  src/Stk.cpp include/Stk.h 
	$(CXX) -c -o $@ $(CPPFLAGS) src/Stk.cpp


#threebees : threebees.cpp
#	$(CXX) $(FLAGS) -o $@ $< 

.PHONY : all clean

clean :
	rm -f $(OBJECTPATH)/*.o
	rm -f $(OUTPUTPATH)/$(PROGRAM)
