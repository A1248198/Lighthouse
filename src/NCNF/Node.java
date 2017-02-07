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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author A1248198
 */
public class Node
{
    List<Node> children;
    public Node(){
        children = new ArrayList<>();
    }
    public void appendChild(Node child){
        children.add(child);
    }

    Node get(int i)
    {
        if(i>=0){
            return children.get(i);
        }
        else{
            return children.get(children.size() + i);
        }
    }
    @Override
    public String toString(){
        String res ="";
        for(Node c : children){
            res +=c.toString() + " ";
        }
        return res;
    }
    
}
