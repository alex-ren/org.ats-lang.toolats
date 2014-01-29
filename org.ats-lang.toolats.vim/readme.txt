Vim plugins for ATS (http://www.ats-lang.org)
======================================

To use all the Vim plugins, add these lines to your $HOME/.vimrc.

  " Some Linux distributions set filetype in /etc/vimrc.
  " Clear filetype flags before changing runtimepath to force Vim to reload them.
  filetype off
  filetype plugin indent off
  set runtimepath+=org.ats-lang.toolats.vim
  filetype plugin indent on
  syntax on

If you want to select fewer plugins, use the instructions in the rest of
this file.

Vim syntax highlighting
-----------------------
To do ....................................
  org.ats-lang.toolats.vim/syntax/dats.vim and 
  org.ats-lang.toolats.vim/syntax/dats.vim haven't been implemented yet.

To install automatic syntax highlighting for ATS programs:

  1. Copy or link the filetype detection script to the ftdetect directory
     underneath your vim runtime directory (normally $HOME/.vim/ftdetect)
  2. Copy or link syntax/dats.vim and syntax/sats.vim to the syntax directory 
     underneath your vim runtime directory (normally $HOME/.vim/syntax). 
     Linking this file rather than just copying it will ensure any changes 
     are automatically reflected in your syntax highlighting.
  3. Add the following line to your .vimrc file (normally $HOME/.vimrc):

     syntax on

In a typical unix environment you might accomplish this using the following
commands:

  mkdir -p $HOME/.vim/ftdetect
  mkdir -p $HOME/.vim/syntax
  ln -s org.ats-lang.toolats.vim/ftdetect/pats.vim $HOME/.vim/ftdetect/
  ln -s org.ats-lang.toolats.vim/syntax/dats.vim $HOME/.vim/syntax/
  ln -s org.ats-lang.toolats.vim/syntax/sats.vim $HOME/.vim/syntax/
  echo "syntax on" >> $HOME/.vimrc



Vim filetype plugins
--------------------

To install one of the available filetype plugins:

  1. Same as 1 above.
  2. Copy or link ftplugin/dats.vim and ftplugin/sats.vim to the ftplugin directory 
     underneath your vim runtime directory (normally $HOME/.vim/ftplugin).
  3. Add the following line to your .vimrc file (normally $HOME/.vimrc):

     filetype plugin on



Vim compiler plugin
-------------------

To install the compiler plugin:

  1. Same as 1 above.
  2. Copy or link compiler/dats.vim and compiler/sats.vim to the compiler directory 
     underneath your vim runtime directory (normally $HOME/.vim/compiler).



Vim indentation plugin
----------------------
To do ...............


