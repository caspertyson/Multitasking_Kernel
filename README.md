
Find the lines which have text that correspond to the custom regex.

COMMAND: 
java REcompile <regex> | REsearch <text> > output.txt

REGEX SPECIFICATIONS
1. any symbol that does not have a special meaning (as given below) is a literal that matches itself
2. . is a wildcard symbol that matches any literal
3. * indicates closure (zero or more occurrences) on the preceding regexp
4. + indicates that the preceding regexp can occur one or more times
5. ? indicates that the preceding regexp can occur zero or one time
6. | is an infix alternation operator such that if r and e are regexps, then r|e is a regexp that matches one of either r or e
7. ( and ) may enclose a regexp to raise its precedence in the usual manner; such that if e is a regexp, then (e) is a regexp and is equivalent to e. e cannot be empty.
8. \ is an escape character that matches nothing but indicates the symbol immediately following the backslash loses any special meaning and is to be interpretted as a literal symbol
9. square brackets "[" and "]" enclose a list of symbols of which one and only one must match (i.e. a shorthand for multi-symbol alternation); all special symbols lose their special meaning within the brackets, and if the closing square bracket is to be a literal then it must be first in the enclosed list; and the list cannot be empty.
10. adjacent regexps are concatenated to form a single regexp
11. operator precedence is as follows (from high to low):
 - escaped characters (i.e. symbols preceded by \)
 - parentheses (i.e. the most deeply nested regexps have the highest precedence)
 - repetition/option operators (i.e. *, + and ?)
 - concatenation
 - alternation (i.e. | and [ ])


GRAMMER:

E -> T
E -> T|E
T -> F
T -> F*
T -> F+
T -> F?
T -> FT
F -> v
F -> .
F -> \
F -> (E)


VOCABULARY:
All symbols are allowed, except for the following:
*,|,?,+,*,(,),π,µ

µ = branch state in the FSM
π = wild card in the FSM