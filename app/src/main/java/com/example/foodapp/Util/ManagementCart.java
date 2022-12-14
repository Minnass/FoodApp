package com.example.foodapp.Util;

import com.example.foodapp.Model.FoodModel;

import java.util.List;

//public class ManagementCart     {
//
//
//    public static void insertFood(List<ItemCartModel> list,FoodModel foodModel)
//    {
//
//        int position=-1;
//        boolean existsAlready=false;
//        for(int i=0;i<list.size();i++)
//        {
//            if(list.get(i).getNameProduct()==foodModel.getName())
//            {
//                position=i;
//                existsAlready=true;
//                break;
//            }
//        }
//       if(existsAlready)
//       {
//           list.get(position).setQuantity(list.get(position).getQuantity()+1);
//       }
//       else
//       {
//          // list.add(new ItemCartModel(1,foodModel.getName(),foodModel.getId(),foodModel.getPrice()));
//       }
//    }
//    public  static float getTotalFee(List<ItemCartModel>list)
//    {
//        float result=0;
//        for(int i=0;i<list.size();i++)
//        {
//            result+=list.get(i).getPrice()*list.get(i).getQuantity();
//        }
//        return result;
//    }
//    public  static  float getTotalFeeOfOneType(float itemPrice,int quantity)
//    {
//        return  Math.round((quantity*itemPrice*100.0)/100.0);
//    }
//}
