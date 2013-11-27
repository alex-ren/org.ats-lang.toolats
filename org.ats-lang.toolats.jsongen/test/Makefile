

######

ATSHOMEQ="$(ATSHOME)"
PATSHOMEQ="$(PATSHOME)"

######

ATSCC=$(ATSHOMEQ)/bin/atscc
ATSCCFLAGS=
ATSRUNTIME=$(ATSHOMEQ)/ccomp/runtime

######
ATSCTRB=$(ATSHOMEQ)/contrib
######

ATSLIB=$(ATSHOMEQ)/ccomp/lib
ATSLIB64=$(ATSHOMEQ)/ccomp/lib64

######

CCFLAGS=-fPIC -O2

######

CFLAGS_JANSSON=$(shell pkg-config jansson --cflags)
LDFLAGS_JANSSON=$(shell pkg-config jansson --libs)
INCLUDE:=-I $(PATSHOMEQ)/src -I $(ATSCTRB) $(CFLAGS_JANSSON)
INCLATS:=-IATS $(PATSHOMEQ) -IATS $(ATSHOMEQ) -IATS $(ATSCTRB)

######

ifeq ($(CFLAGS_JANSSON), )
$(error Cannot locate jansson. Please run e.g.  \
  export PKG_CONFIG_PATH=${HOME}/programs/install/lib/pkgconfig:${PKG_CONFIG_PATH})
endif

######

prog=test

######

all:: $(prog)


SOURCES = \
  json_simple.sats \
  json_simple.dats \
  filename1.sats \
  filename2.sats \
  filename2.dats \
  filename3.sats \
  filename3.dats \
  test_main.dats \
  \
  test_cvt.sats \
  test_cvt.dats \
  test_cvt_impl.dats \
  \
  test_cvt_aux.dats \



######

OBJECTS := $(patsubst %.sats, %_sats.o, $(SOURCES))
OBJECTS := $(patsubst %.dats, %_dats.o, $(OBJECTS))

######

$(prog):: $(OBJECTS)
	$(ATSCC) -o $@ $^ $(LDFLAGS_JANSSON)

######

%_sats.o: %.sats
	$(ATSCC) $(INCLATS) $(INCLUDE) $(CCFLAGS) -c $< -o $@
%_dats.o: %.dats
	$(ATSCC) $(INCLATS) $(INCLUDE) $(CCFLAGS) -c $< -o $@


######

# libatsyn2json:: libatsyn2json_sats.o
# libatsyn2json_sats.o: SATS/libatsyn2json.sats
# 	$(ATSCC) $(INCLATS) $(INCLUDE) $(CCFLAGS) -c $<; $(AR) -r libatsyn2json.a $@
# 
# libatsyn2json:: libatsyn2json_dats.o
# libatsyn2json_dats.o: DATS/libatsyn2json.dats
# 	$(ATSCC) $(INCLATS) $(INCLUDE) $(CCFLAGS) -c $<; $(AR) -r libatsyn2json.a $@

RM=rm -rf

clean:
	$(RM) *_?ats.o
	$(RM) *_?ats.c
	$(RM) $(prog)













