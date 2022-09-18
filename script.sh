#!/bin/bash
java SorterServer &
sleep 3
java DummyConcurrentTester &
java DummyConcurrentTester2 &
java DummyConcurrentTester3 &
java SorterClient 1 isEmpty >> output.txt
java SorterClient 1 push 30
java SorterClient 1 push 33
java SorterClient 1 push 50
java SorterClient 1 push 11
java SorterClient 1 push 12
java SorterClient 1 push 21
java SorterClient 1 isEmpty >> output.txt
java SorterClient 1 print >> output.txt
java SorterClient 1 pop >> output.txt
java SorterClient 1 print >> output.txt
java SorterClient 1 pushop ascending
java SorterClient 1 print >> output.txt
java SorterClient 2 isEmpty >> output.txt
java SorterClient 2 push 3
java SorterClient 2 push 7
java SorterClient 2 push 11
java SorterClient 2 push 21
java SorterClient 2 push 101
java SorterClient 2 push 93
java SorterClient 2 print >> output.txt
java SorterClient 2 pushop descending
java SorterClient 2 print >> output.txt
java SorterClient 1 print >> output.txt
java SorterClient 1 pushop min
java SorterClient 2 pushop max
java SorterClient 1 print >> output.txt
java SorterClient 2 print >> output.txt
java SorterClient 3 isEmpty >> output.txt
java SorterClient 3 push 3
java SorterClient 3 push 50
java SorterClient 3 push -5
java SorterClient 3 push 77
java SorterClient 3 print >> output.txt
java SorterClient 3 delayPop 100 >> output.txt
java SorterClient 3 push 83
java SorterClient 3 pushop min
java SorterClient 3 print >> output.txt
java SorterClient 1 print >> output.txt
java SorterClient 2 print >> output.txt
java SorterClient 3 isEmpty >> output.txt
java SorterClient 1 push 21
java SorterClient 1 push 83
java SorterClient 1 push 52
java SorterClient 1 push 107
java SorterClient 1 print >> output.txt
java SorterClient 1 pushop descending
java SorterClient 1 print >> output.txt
java SorterClient 1 pop >> output.txt
java SorterClient 1 pop >> output.txt
java SorterClient 1 pop >> output.txt
java SorterClient 2 push 303
java SorterClient 2 push 25
java SorterClient 2 push 211
java SorterClient 2 push 63
java SorterClient 2 push -38
java SorterClient 2 print >> output.txt
java SorterClient 2 pushop ascending
java SorterClient 2 print >> output.txt
java SorterClient 2 pop >> output.txt
java SorterClient 2 pop >> output.txt
java SorterClient 2 delayPop 150 >> output.txt
java SorterClient 2 pop >> output.txt
java SorterClient 2 pop >> output.txt
java SorterClient 2 pop >> output.txt
java SorterClient 1 print >> output.txt
java SorterClient 2 print >> output.txt
java SorterClient 3 print >> output.txt
java SorterClient 2 isEmpty >> output.txt
java SorterClient 1 isEmpty >> output.txt
diff output.txt ExpectedOutput.txt


