#!/bin/bash
# By Fisher Evans

function genFileVars {
  FULLPATH=$(readlink -f $1)
  FILE=$(basename $FULLPATH)
  FOLDER=$(dirname $FULLPATH)
  SRCFILE=$SRC$FOLDER/$FILE.java
  OUTFILE=$OUT$FOLDER/$FILE.class
  FOLDERPACKAGE=$(echo $FOLDER | sed 's/\//\./g' | sed 's/^\.//'i)
}

function readHeader {
   while read line; do
    if [[ ${line:0:1} == '#' ]]; then
      if [[ ${line:2:3} == 'lib' ]]; then
        CP=$CP:${line:6}
      fi
    else
      break
    fi
  done < $FULLPATH
}

function softCompile {
  ISBAVA="false"
  if [ -f $FULLPATH ] && [[ $(head -n1 $FULLPATH) == "#!/usr/bin/env bava" ]];then
    if [ ! -f $OUTFILE ] || [ $FULLPATH -nt $OUTFILE ] || [[ $FMODE == "true" ]]; then
      compile
    else
      if [[ $VMODE == "true" ]]; then echo ">>> Compile not required. Cached version is up to date: $OUTFILE"; fi
    fi
    ISBAVA="true"
  else
    echo "Not BAVA File: $FULLPATH"
  fi
}

function compile {
  mkdir -p $(dirname $SRCFILE)
  echo "package $FOLDERPACKAGE;" > $SRCFILE
  readHeader
  if [[ $VMODE == "true" ]]; then echo ">>> Generating SRC file: $SRCFILE"; fi
  cat $FULLPATH | sed '/^#/ d' >> $SRCFILE
  FAILED="false"
  if [[ $VMODE == "true" ]]; then echo ">>> Compiling cached file: $OUTFILE"; fi
  if [[ $VMODE == "true" ]]; then echo ">>> Using classpath: $CP"; fi
  javac -cp $CP -d $OUT $SRCFILE
  if [[ $? != 0 ]]; then FAILED="true"; fi
}

function run {
  softCompile
  if [[ $FAILED != "true" ]] && [[ $ISBAVA == "true" ]]; then
    readHeader
    if [[ $VMODE == "true" ]]; then echo ">>> Running file: $OUTFILE"; fi
    if [[ $VMODE == "true" ]]; then echo ">>> Using classpath: $CP"; fi
    if [[ $VMODE == "true" ]]; then echo ">>> Passing arguments: $*"; fi
    java -cp $CP $FOLDERPACKAGE.$FILE $*
  fi
}

IFS=''
if [ -z "$BAVAROOT" ]; then BAVAROOT=~/.bava; fi
SRC=$BAVAROOT/src
OUT=$BAVAROOT/out
LIB=$BAVAROOT/lib
mkdir -p $SRC $OUT $LIB

CP=$OUT:$LIB/*
if [ ! -z "$BAVACP" ]; then CP=$CP:$BAVACP; fi

ISBAVA="false"
GMODE="false"
CMODE="false"
FMODE="false"
XMODE="false"
VMODE="false"
if [[ $1 == -* ]]; then
  if [[ $1 == *c* ]]; then CMODE="true"; fi
  if [[ $1 == *f* ]]; then FMODE="true"; fi
  if [[ $1 == *g* ]]; then GMODE="true"; fi
  if [[ $1 == *v* ]]; then VMODE="true"; fi
  if [[ $1 == *x* ]]; then
    XMODE="true"
    echo 'Delete the following folders?'
    echo '$BAVAROOT/src'
    echo '$BAVAROOT/out'
    read -p '[y/n] ' input
    if [ $input == "y" ]; then
      rm -rf $BAVAROOT/out
      rm -rf $BAVAROOT/src
    fi
    exit 0
  fi
  shift
fi

if [ -z "$1" ] && [[ $XMODE != "true" ]]; then
  echo "BAVA = Bash Java File"
  echo "Global Variables:"
  echo "  BAVACP: Global Java classpath addition (default: '')"
  echo "  BAVAROOT: Folder used for caching (default: '~/.bava')"
  echo "Commands:"
  echo "  Run a BAVA file: bava (filename)"
  echo "  Compile a BAVA file: bava -c[f] (filename) [other ... files]"
  echo "  Generate a BAVA skeleon: bava -g (filename)"
  echo "  Clear the BAVA cache: bava -x"
  echo "Notes:"
  echo "  BAVA files can be run like scripts by using: #!/usr/bin/env bava"
  echo '  $BAVAROOT/lib is always included in the classpath'
  echo "  A BAVA file's folder is its package (/var/log/bava => var.log.bava)"
  echo "  Add the affitional 'f' argument to force compile (ignore the cache)"
  echo "  Add the affitional 'v' argument to print messages"
  exit 0
fi

if [[ $GMODE == "true" ]]; then
  if [[ $VMODE == "true" ]]; then echo ">>> Generating a BAVA skeleton file: $1"; fi
  if [ -f $1 ]; then
    echo "File already exists: $1"
    exit 1
  fi
  echo "#!/usr/bin/env bava" > $1
  echo "# vim:ft=java" >> $1
  echo "# #lib:/path/to.jar" >> $1
  echo "" >> $1
  echo "// This object's package is set at compile. Do not set it." >> $1
  echo "// import packages.are.folder.paths;" >> $1
  echo "" >> $1
  echo "public class $1 {" >> $1
  echo "  public static void main(String[] args) {" >> $1
  echo "" >> $1
  echo "  }" >> $1
  echo "}" >> $1
  if [[ $VMODE == "true" ]]; then echo ">>> Making generated file executable"; fi
  chmod +x $1
  exit
elif [[ $CMODE == "true" ]]; then
  while [[ $# > 0 ]]; do
    genFileVars $1
    shift
    softCompile
  done
else
  genFileVars $1
  shift
  run $@
fi