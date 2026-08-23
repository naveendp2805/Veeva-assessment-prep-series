/*
 * Problem: Server Connection Pool
 *
 * Design a connection pool for a distributed system containing N servers.
 * Each server maintains the number of currently active connections.
 *
 * Whenever a new connection/request arrives, it must be assigned to the
 * server having the minimum number of active connections.
 *
 * Requirements:
 * - Accept the number of servers dynamically.
 * - Accept the initial number of active connections for each server.
 * - Assign new connections to the server with the minimum active connections.
 * - Increase the server's connection count when a connection is assigned.
 * - Remove a connection from a specified server when it finishes.
 * - Display the current connection count of all servers.
 *
 * Example:
 *
 * Server 1 -> 4 connections
 * Server 2 -> 5 connections
 * Server 3 -> 2 connections
 *
 * New connection -> Server 3
 *
 * After assignment:
 * Server 1 -> 4
 * Server 2 -> 5
 * Server 3 -> 3
 *
 * OOP Concepts:
 * - Encapsulation
 * - Classes and Objects
 * - Constructor
 * - Has-a relationship
 * - ArrayList
 *
 * Approach:
 * Create a Server class to represent each server and encapsulate
 * its connection count.
 *
 * Create a ConnectionPool class that maintains all servers and
 * is responsible for selecting the server with the minimum
 * active connections.
 *
 * Time Complexity:
 * Assigning a connection: O(n)
 * Removing a connection: O(n)
 *
 * Space Complexity: O(n)
 *
 * n = number of servers
 */

import java.util.*;

class Server
{
    private int serverId;
    private int activeConnections;

    public Server(int serverId, int activeConnections) {
        this.serverId = serverId;
        this.activeConnections = activeConnections;
    }

    public int getServerId() {
        return serverId;
    }

    public int getActiveConnections() {
        return activeConnections;
    }

    public void addConnection() {
        activeConnections++;
    }

    public void removeConnection() {
        if(activeConnections > 0) activeConnections--;
    }

    @Override
    public String toString() {
        return "server " + serverId + " -> " + activeConnections + " connections"; 
    }
}

class ConnectionPool
{
    private static List<Server> servers;

    public ConnectionPool(List<Server> servers) {
        this.servers = servers;
    }

    private static Server getMinimumConnectionServer() {
        Server minServer = servers.get(0);

        for(Server server : servers)
            if(server.getActiveConnections() < minServer.getActiveConnections())
                minServer = server;

        return minServer;
    }

    public void assignConnection(){
        Server server = getMinimumConnectionServer();

        server.addConnection();

        System.out.println("Connection assigned to server " + server.getServerId());
    }

    public void removeConnection(int serverId) {
        for(Server server : servers)
        {
             if(server.getServerId() == serverId)
            {
                server.removeConnection();
                return;
            }
        }
    }

    public void displayServers() {
        for(Server server : servers)
            System.out.println(server);
    }
}

public class ServerConnectionPool 
{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter no.of servers: ");
        int n = sc.nextInt();

        List<Server> servers = new ArrayList<>();

        for(int i=0; i<n; i++)
        {
            System.out.println("Enter active connections for server " + i+1);
            int connections = sc.nextInt();

            servers.add(new Server(i+1, connections));
        }

        ConnectionPool pool = new ConnectionPool(servers);

        while(true)
        {
            System.err.println("1:assignConnection 2:removeConnection 3:displayServers 4:exit");
            System.err.println("Enter your choice: ");
            int ch = sc.nextInt();

            switch(ch)
            {
                case 1 : pool.assignConnection();
                         System.out.println("\nServer Status After Assigning Connections:");
                         pool.displayServers();
                         break;

                case 2 : System.out.println("Enter serverId: ");
                         int id = sc.nextInt();
                         pool.removeConnection(id);

                         System.out.println("\nServer Status After Removing Connection:");
                         pool.displayServers();
                         break;

                case 3 : pool.displayServers();
                         break;

                case 4 : return;
            }
        }
    }
}
