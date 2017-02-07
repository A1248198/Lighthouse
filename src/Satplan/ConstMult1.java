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
package Satplan;

import java.util.List;

/**
 *
 * @author A1248198
 */
public class ConstMult1 {
        int count;
        int indices[];
        String[] ca;
        int listSize ;
        public ConstMult1(int count,List<String> li){
            this.count = count;
            indices = new int[count];
            ca = (String[]) li.toArray(new String[1]);
            listSize = ca.length;
        }
        public String getConstantForIndex(int i){
            return ca[indices[i]];
        }
        @Override
        public String toString(){
            String res = "";
            for(int i = 0; i< count ;i+=1){
                res+=ca[indices[i]];
            }
            return res;
        }
        public boolean hasNext()
        {
            if(count ==0){
                return false;
            }
            if(indices[0]==listSize){
                return false;
            }
            else{
                return true;
            }
        }
        public String get(int i){
            return ca[indices[i]];
        }
        public String[] get()
        {
            String[] res = new String[count];
            for(int i =0 ; i<count; i+=1){
                res[i] = ca[indices[i]];
            }
            return res;
        }
        public void moveToNext(){
            indices[count-1]+=1;
            for(int i = count -1 ; i>0;i-=1){
                if(indices[i] >= listSize  ){
                    indices[i-1]+=1;
                    indices[i] = 0;
                }
            }
            
        }
        
    } 