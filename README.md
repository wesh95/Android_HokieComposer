# Android_HokieComposer

This Application exercises the topics of Services and BroadcastReceiver. The task examines my
ability to create and manage background services across different Android lifecycle events. 
The application to be implemented is a Hokie Football music composer app to play and mix music. 
The requirements are:

1. The application must have two screens: an editing screen and a playing screen. The application
   must provide proper ways to navigate between two screens. It can use either two Activities or two Fragments
    to implement the two-screen design.
  
2. On the editing screen, there must be three sections.

    Section 1 is the background music selector. It lets the user select a background music from a Spinner. 
    The spinner shows at least 3 songs pre-loaded in the resource folder. You must use the “Go Tech Go” as the default music 
    (available in /res/raw directory). And you can select 2 other Hokie music files you like.
  
    Section 2 is the overlapping sound track editor. It lets the user add overlapping audio track on the background music. 
    The user can add three overlapped sound effects. The user can pick one of the three sounds (Clapping, Cheering and Go Hokies) 
    from each spinner. The overlapped sound is shorter than the background music (usually <10 seconds). 
    The SeekBar allows the user to select where to insert the sound effect 
    (i.e. at what point of time should the overlapping music start playing).
  
    Section 3 is at the bottom of the screen. It has a “Play” button to let the user goes to the second screen.
  
3. On the playing screen, the user can play the composed music.

    On the top it shows the name of the Hokie music that is being played in the background and a default picture. 
    When the music reaches the selected time from the SeekBar, the corresponding sound effect should start playing. 
    When the overlapped sound track is played, it shows a picture reflects that sound effect. When the overlapping sound ends, 
    the image goes back to the default picture.
  
    At the bottom there are two buttons. The user can play/pause the music with the first button (the text should change properly), 
    and can restart the music with the second button.
  
4. The music track must be played in a Service. So that even the user leave the app or opens another app, the music must keep playing. 
  The name of the music, the picture, and the overlapping sound must be properly shown when the user leaves and then comes back to
  the app.
  
5. The application must support multiple iterations of editing. You don’t have to save the edited music. 
  But the user can go back to the editing screen, select a different background music and adjust the overlapping sound effects, 
  and play the music again.
  
SCREENSHOTS:

Editing Screen (left). Playing Screen with mario Overlap track(Right)

![Image](https://github.com/wesh95/Android_HokieComposer/blob/master/HokieComposer_Screenshots/composer1.JPG)
![Image](https://github.com/wesh95/Android_HokieComposer/blob/master/HokieComposer_Screenshots/composer2.JPG)
