
RM = rm -f

target=test

all: $(target)

$(target): test.dats
	patscc test.dats -o test

.PHONY: clean

clean:
	$(RM) *_?ats.c
	$(RM) $(target)

