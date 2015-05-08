#include "Plucked.h"
#include "Messager.h"
#include "Voicer.h"
#include "FileWvOut.h"
#include "SKINI.msg"

#include <algorithm>
#include <iostream>

// Struttura dati fondamentale 

struct TickData {
  Voicer voicer;
  Messager messager;
  FileWvOut out;
  Skini::Message message;
  int counter;
  bool haveMessage;
  bool done;

  TickData()
    : counter(0), haveMessage(false), done( false ) {}
};

#define DELTA_CONTROL_TICKS 64 

// Metodo per l'elaborazione dei messaggi SKINI

void processMessage( TickData* data )
{
  register StkFloat value1 = data->message.floatValues[0];
  register StkFloat value2 = data->message.floatValues[1];

  switch( data->message.type ) {

  case __SK_Exit_:
    data->done = true;
    return;

  case __SK_NoteOn_:
    if ( value2 == 0.0 ) // velocity is zero ... really a NoteOff
      data->voicer.noteOff( value1, 64.0 );
    else { // a NoteOn
      data->voicer.noteOn( value1, value2 );
    }
    break;

  case __SK_NoteOff_:
    data->voicer.noteOff( value1, value2 );
    break;

  case __SK_ControlChange_:
    data->voicer.controlChange( (int) value1, value2 );
    break;

  case __SK_AfterTouch_:
    data->voicer.controlChange( 128, value1 );

  case __SK_PitchChange_:
    data->voicer.setFrequency( value1 );
    break;

  case __SK_PitchBend_:
    data->voicer.pitchBend( value1 );

  } // end of switch

  data->haveMessage = false;
  return;
}


// Metodo per la creazione effettiva dei samples

int tick(char *buffer, int bufferSize, void *dataPointer)
{
  TickData *data = (TickData *) dataPointer;
  int counter, nTicks = bufferSize;

  while ( nTicks > 0 && !data->done ) {

    if ( !data->haveMessage ) {
      data->messager.popMessage( data->message );
      if ( data->message.type > 0 ) {
        data->counter = (long) (data->message.time * Stk::sampleRate());
        data->haveMessage = true;
      }
      else
        data->counter = DELTA_CONTROL_TICKS;
    }

    counter = std::min( nTicks, data->counter );
    data->counter -= counter;

    for ( int i=0; i<counter; i++ ) {
      data->out.tick(data->voicer.tick());
      nTicks--;
    }
    if ( nTicks == 0 ) break;

    if ( data->haveMessage ) processMessage( data );
  }

  return 0;
}


int main(int argc, char * argv[]) {
  
  Stk::setSampleRate( 44100.0 );
  //Stk::setRawwavePath( "rawwaves/" );

  int i;
  TickData data;

  // Creazione del file di Output

  try {
    data.out.openFile(argv[2], 1, FileWrite::FILE_WAV, Stk::STK_SINT16);
  }
  catch (StkError& error) {
    error.printMessage();
    goto cleanup;
  }

  // Inizializzazione delle varie voci dello strumento
  
  Instrmnt *instrument[3];
  for ( i=0; i<3; i++ ) instrument[i] = 0;
  
  try {
    for ( i=0; i<3; i++ )
      instrument[i] = new Plucked(5.0); //BeeThree();
  }
  catch (StkError &) {
    goto cleanup;
  }

  for ( i=0; i<3; i++ )
    data.voicer.addInstrument( instrument[i] );

  // Inizializzazione dell'input skini(midi) da file
  
  if ( data.messager.setScoreFile(argv[1]) == false )
    goto cleanup;

  // Elaborazione e costruzione del file wav
  
  std::cout<< "File in elaborazione. Attendere ... \n";
  
  while ( !data.done ) {
    tick(NULL, 256, (void *)&data);
  }

  // Arresto ed uscita
  
 cleanup:
  for ( i=0; i<3; i++ ) delete instrument[i];

  return 0;
}
