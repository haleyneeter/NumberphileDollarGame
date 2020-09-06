package edu.umsl;
/*
title: Dollar Game
name of file: Main.java
external files: Graph.java, Vertex.java
created files: none
programmer names: Haley Neeter, Trey Klotz
programmer email: hsn6xc@umsystem.edu, tkpgh@mail.umsl.edu
course number: CMPSCI4500
date: 9/06/2020
explanation of program: Attempts to make playing the dollar game easier and automated
external resources: 
 */

import java.util.Scanner;
import static java.lang.Character.toUpperCase;

public class Main{

    public static void main(String[] args){
        int V = 0; //Number of vertices
        int E = 0; //Number of edges
        int countConnects=0;
        boolean InvalVert=true; //check for valid vertex
        boolean InvalEd=true; //check for valid edge
        boolean InvalStg=true; //check for valid ???
        boolean GameWon = false;
        int testDollars=0;
        int totalDollars = 0;
        String alphabet="";
        Graph<Vertex> graph = new Graph<Vertex>();
        int movesMade = 0;
        Scanner input = new Scanner(System.in);

        System.out.println("We're going to play the dollar game!\n" +
                "I'll ask for a number of vertices and edges and you'll specify how many of each and which ones are connected.\n" +
                "Then we'll set a starting value for each vertex!\n" +
                "Then, you play the game by giving from one vertex to all connected vertices (x <- y -> z)\n" +
                "or by taking from all connected vertices to one vertex (x -> y <- z).\n" +
                "You win when all the vertices have a positive amount inside of them.\n" +
                "Be careful though! The game can be set up to be impossible to win. Good luck!");

        System.out.println("Please enter the number of vertices you want: ");
        V= input.nextInt();
        while(InvalVert)
        {
            if(V<2)
            {
                System.out.println("Must have at least 2 vertices");
                System.out.println("Please enter the number of vertices you want: ");
                V= input.nextInt();
            }
            else if(V>7)
            {
                System.out.println("Must have no more than 7 vertices");
                System.out.println("Please enter the number of vertices you want: ");
                V= input.nextInt();
            }
            else
            {
                InvalVert=false;
            }
        }

        //collapse alphabet choosing into one statement
        alphabet = "ABCDEFG ".substring(0,V+1);

        Vertex [] arr = new Vertex [V];
        for(int i=0; i<V; i++)
        {
            arr[i] = new Vertex();
            arr[i].setName(i);
            System.out.println("The Vertex "+ (i+1) +" will now be refered to by it's name " + arr[i].name);
            System.out.println("Please enter the initial dollar amount for vertex "+ (i+1) +" (Note: number can be negative)");
            testDollars=input.nextInt();
            totalDollars+=testDollars;
            arr[i].setDollars(testDollars);
            arr[i].setConnectedWith(countConnects);
            graph.addVert(arr[i]);

        }
        System.out.println("Please enter how many Edges you'd like: ");
        E=input.nextInt();
        while(InvalEd)
        {
            if(E<(V-1))
            {
                System.out.println("Must have at least " +(V-1) +" edges");
                System.out.println("Please enter the number of edges you want: ");
                E= input.nextInt();
            }
            else if(E>(2*V))
            {
                System.out.println("Must have no more than " +(2*V) +" edges");
                System.out.println("Please enter the number of edges you want: ");
                E= input.nextInt();
            }
            else
            {
                InvalEd=false;
            }
        }
        System.out.println("Now it's time to connect edges");

        String edges = "";
        for(int j=0; j<E; j++)
        {
            System.out.println("Give two character string of vertex names (ie AD) that will connect two vertices together");
            String Line=input.nextLine().toUpperCase();
            while(InvalStg)
            {
                if(Line.length()!=2)
                {
                    System.out.println("String should only contain 2 characters");
                    System.out.println("Give two character string of vertex names (ie AD) that will connect two vertices together");
                    Line=input.nextLine().toUpperCase();
                }

                char c1=Line.charAt(0);
                char c2=Line.charAt(1);
                if(alphabet.indexOf(c1)==-1)
                {
                    System.out.println("The first character in your string is not a valid vertex name");
                    System.out.println("Give two character string of vertex names (ie AD) that will connect two vertices together");
                    Line=input.nextLine().toUpperCase();

                }
                else if (alphabet.indexOf(c2)==-1)
                {
                    System.out.println("The second character in your string is not a valid vertex name");
                    System.out.println("Give two character string of vertex names (ie AD) that will connect two vertices together");
                    Line=input.nextLine().toUpperCase();
                }
                else if (c1 == c2){
                    System.out.println("You can not connect a vertex to itself");
                    System.out.println("Give two character string of vertex names (ie AD) that will connect two vertices together");
                    Line = input.nextLine().toUpperCase();
                }
                else if (edges.contains(Line) || edges.contains(new StringBuilder(Line).reverse().toString())){
                    System.out.println("You have already added that edge");
                    System.out.println("Give two character string of vertex names (ie AD) that will connect two vertices together");
                    Line = input.nextLine().toUpperCase();
                }
                else
                {
                    InvalStg=false;
                }

            }
            InvalStg = true;
            edges += Line + ",";

            char ch1=Line.charAt(0);
            char ch2=Line.charAt(1);
            int first=(alphabet.indexOf(ch1));
            int next=(alphabet.indexOf(ch2));

            graph.addEdge(arr[first],arr[next]);
            arr[first].connectedWith=(arr[first].connectedWith)+1;
            arr[next].connectedWith=(arr[next].connectedWith)+1;
            String s1=Line.valueOf(ch1);
            String s2=Line.valueOf(ch2);
            arr[first].sharedEdges.add(s2);
            arr[next].sharedEdges.add(s1);

        }
        System.out.println("Time to play the game!");
        if (totalDollars < 0) System.out.println("This game is unwinnable, but you can continue to play.");
        boolean playing=true;
        while(playing)
        {
            //check for a winning game
            GameWon = true;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].dollars < 0) GameWon = false;
            }
            if (GameWon) System.out.println("All dollar amounts are non-negative, you've won! You may continue playing if you want.");

            System.out.println("Enter G to give to a vertex, T to take from a Vertex or Q to quit");
            char game=input.next().charAt(0);
            switch(toUpperCase(game))
            {
                case'T':
                {
                    System.out.println("What vertex would you like to take from?");
                    char takeIt=toUpperCase(input.next().charAt(0));
                    int found=(alphabet.indexOf(takeIt));
                    int subtract =arr[found].connectedWith;
                    arr[found].dollars=(arr[found].dollars)-subtract;
                    for(int k=0; k<arr[found].sharedEdges.size();k++)
                    {
                        String testStrg=arr[found].sharedEdges.get(k);
                        char testChar=toUpperCase(testStrg.charAt(0));
                        int testInt=(alphabet.indexOf(testChar));
                        arr[testInt].dollars=(arr[testInt].dollars)+1;

                    }

                    movesMade++;
                    for(int n=0;n<V; n++)
                    {
                        System.out.println("Vertex "+ arr[n].name+ " has "+ arr[n].dollars +" dollars.");
                    }
                    break;
                }
                case'G':
                {
                    System.out.println("What vertex would you like give money to?");
                    char takeIt=toUpperCase(input.next().charAt(0));
                    int found=(alphabet.indexOf(takeIt));
                    int add =arr[found].connectedWith;
                    arr[found].dollars=(arr[found].dollars)+add;
                    for(int k=0; k<arr[found].sharedEdges.size();k++)
                    {
                        String testStrg=arr[found].sharedEdges.get(k);
                        char testChar=toUpperCase(testStrg.charAt(0));
                        int testInt=(alphabet.indexOf(testChar));
                        arr[testInt].dollars=(arr[testInt].dollars)-1;

                    }
                    movesMade++;
                    for(int n=0;n<V; n++)
                    {
                        System.out.println("Vertex "+ arr[n].name + " has "+ arr[n].dollars +" dollars.");
                    }
                    break;

                }
                case'Q':
                {
                    System.out.println("Game has been quit");
                    System.out.println("You made " + movesMade + " moves.");
                    playing=false;
                    break;

                }
                default:
                    System.out.println("Error");
            }
        }
    }
}
