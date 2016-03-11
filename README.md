This assignment contains two exercises E1 and E2

E1: 

The DATA folder contains language files like german, french, spanish, etc. which have to-English translations. The goal of this assignment is to merge all these files into a single file that will have an English words meaning in all the five languages

How to Run:  
1) Execute the rebuild.sh file

2) Execute the rerun.sh file

Sample Output:

abandoned: [Adjective]french: abandonne/, abandonnes, de/vergonde/|german: verlassen|italian:N/A|portuguese: desertus, perditus|spanish:  desertus, perditus

Note: 

1) A line is considered to be a valid record only if it contains a part of speech at the end.

2) A word can have multiple meanings and will be comma separated

3) If a word does not have a translation in that particular language, mark it as N/A

E2:

The goal of this assignment is to learn how to do a map-side join. In this assignment, we will use the output of E1 as input to our Mapper and will join with other file latin.txt to get Latin translations as well

How to Run:  
1) Execute the rebuild.sh file

2) Execute the rerun.sh file

Sample Output:

abandoned: [Adjective]french: abandonne/, abandonnes, de/vergonde/|german: verlassen|italian:N/A|portuguese: desertus, perditus|spanish:  desertus, perditus|latin: desertus, perditus
