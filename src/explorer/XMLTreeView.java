/* 
 * Copyright (C) 2017 A1248198
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package explorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Event.*;
import java.io.*;
import javax.swing.tree.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import org.apache.xerces.parsers.*;

public class XMLTreeView {

       private SAXTreeBuilder saxTree = null;
       private static File file ;
       
       public static void main(String args[]){
            show(null);
       }
       public static void show(File xml){
              JFrame frame = new JFrame("Lighthouse Expolorer");
              frame.setSize(400,400);
              
              frame.addWindowListener(new WindowAdapter(){
                   public void windowClosing(WindowEvent ev){
                       System.exit(0);
                   }
              });
              //frame.addMouseListener(null);
              if(xml == null){
                  final JFileChooser fc = new JFileChooser();
                  fc.showOpenDialog(frame);
                  xml = fc.getSelectedFile();
              }
              file = xml;
              new XMLTreeView(frame);
       }
       
       public XMLTreeView(JFrame frame){ 
              frame.getContentPane().setLayout(new BorderLayout());  
              DefaultMutableTreeNode top = new DefaultMutableTreeNode(file.getName());
//              DefaultMutableTreeNode top = new DefaultMutableTreeNode("XML Document"); 
              
              saxTree = new SAXTreeBuilder(top); 
              
              try {             
              SAXParser saxParser = new SAXParser();
              saxParser.setContentHandler(saxTree);
              saxParser.parse(new InputSource(new FileInputStream(file)));
              }catch(Exception ex){
                 top.add(new DefaultMutableTreeNode(ex.getMessage()));
              }
              JTree tree = new JTree(saxTree.getTree()); 
              JScrollPane scrollPane = new JScrollPane(tree);
              
              frame.getContentPane().add("Center",scrollPane);                                           
              frame.setVisible(true);       
              
        } 
        
     
}
class SAXTreeBuilder extends DefaultHandler{
       
       private DefaultMutableTreeNode currentNode = null;
       private DefaultMutableTreeNode previousNode = null;
       private DefaultMutableTreeNode rootNode = null;

       public SAXTreeBuilder(DefaultMutableTreeNode root){
              rootNode = root;
       }
       public void startDocument(){
              currentNode = rootNode;
       }
       public void endDocument(){
       }
       public void characters(char[] data,int start,int end){
              String str = new String(data,start,end);              
              if (!str.equals("") && !str.matches("\\s*") ){//&& Character.isLetter(str.charAt(0)))
                  currentNode.add(new DefaultMutableTreeNode(str));           
                  //System.out.println(str);
              }
       }
       public void startElement(String uri,String qName,String lName,Attributes atts){
              previousNode = currentNode;
              currentNode = new DefaultMutableTreeNode(lName);
              // Add attributes as child nodes //
              attachAttributeList(currentNode,atts);
              previousNode.add(currentNode);              
       }
       public void endElement(String uri,String qName,String lName){
              if (currentNode.getUserObject().equals(lName))
                  currentNode = (DefaultMutableTreeNode)currentNode.getParent();              
       }
       public DefaultMutableTreeNode getTree(){
              return rootNode;
       }
       
       private void attachAttributeList(DefaultMutableTreeNode node,Attributes atts){
               for (int i=0;i<atts.getLength();i++){
                    String name = atts.getLocalName(i);
                    String value = atts.getValue(name);
                    node.add(new DefaultMutableTreeNode(name + " = " + value));
               }
       }
       
}