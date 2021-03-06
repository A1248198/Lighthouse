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
package NCNF;

/**
 *
 * @author A1248198
 */
public class Formula
{
    Node rootNode;

    /**
     * A class to represent a generic Propositional Calculus formula
     * for display purposes only, no checks are performed
     */
    public Formula(){
        rootNode = new Node();
    }
    public Formula(Node root){
        rootNode = root;
    }

    /**
     * Put a new <code>Node</code> in the formula tree.
     * @param what A node to append, not null.
     * @param where  A path to take from the root node to the 'what' node father, a negative value traverses the children list from the right. (for example put(node) will put append node to the root, put(node,0) will append to the roots first child, put(node,-1) to the roots last child.
     */
    public void put(Node what,int ... where){
        Node current = rootNode;
        for(int i : where){
            current = current.get(i);
        }
        current.appendChild(what);
    }
    @Override
    public String toString(){
        return rootNode.toString();
    }
    
}
