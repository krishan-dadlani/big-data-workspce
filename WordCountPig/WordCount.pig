pigWordCount = LOAD './input.txt' using PigStorage() AS (line);

hackathonLineInstance = FOREACH pigWordCount GENERATE 'hackathon' as key, ((org.apache.pig.builtin.LOWER(line) matches '.*hackathon.*')?1:0) as count;
decLineInstance = FOREACH pigWordCount GENERATE 'Dec' as key, ((org.apache.pig.builtin.LOWER(line) matches '.*dec.*')?1:0) as count;
chicagoLineInstance = FOREACH pigWordCount GENERATE 'Chicago' as key, ((org.apache.pig.builtin.LOWER(line) matches '.*chicago.*')?1:0) as count;
javaLineInstance = FOREACH pigWordCount GENERATE 'Java' as key, ((org.apache.pig.builtin.LOWER(line) matches '.*java.*')?1:0) as count; 

hackathonGroup = GROUP hackathonLineInstance BY key;  
decGroup = GROUP decLineInstance BY key;  
chicagoGroup = GROUP chicagoLineInstance BY key;  
javaGroup = GROUP javaLineInstance BY key;  

hackathonTotal = FOREACH hackathonGroup GENERATE group, SUM(hackathonLineInstance.count);
decTotal = FOREACH decGroup GENERATE group, SUM(decLineInstance.count);
chicagoTotal = FOREACH chicagoGroup GENERATE group, SUM(chicagoLineInstance.count);
javaTotal = FOREACH javaGroup GENERATE group, SUM(javaLineInstance.count);

result = union hackathonTotal, decTotal, chicagoTotal, javaTotal;

STORE result INTO './output.out' USING PigStorage();