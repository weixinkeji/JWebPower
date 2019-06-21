在使用JWebPower框架前，我们先来了解以下两个关键词：权限编号；权限等级		  
  
一、权限编号  

      通俗地说，就是对资源进行编号。
      开发中，指请求路径。请求路径一般分为 静态资源请求路径（如图片）；和服务路径（如我们常说的controller方法）
权限编号运用示例  
    示例1：  
        我们对服务路径 /hello/666  进行编号，号码为001  
        那么，拥有这个001号码的用户，就可以访问到 /hello/666  
    示例2：  
        某视频，需要开通[绿钻]的用户才能访问。  
        那么，我们可以用编号 lz 表示表示[绿钻],在发布视频时，我们对视频进行加上标签 [绿钻]。然后 ，我们访问 时，进行校对即可。  
    示例2：  
        某视频，需要开通[绿钻]（编号lz），[青钻] (qz),才能访问。如上类似的解决方式  
      
  
二、权限等级  
    通过地说，就是方便解决一些权限的逻辑问题。比如 用作 [会员等级]  

    比如，某资源，需要12级会员以上，才能访问。
    比如，游戏当中，需要xx级会员，才能开通某些特权，和购买一些vip包。
    比如，配合上面的权限编号，需要 会员1级以上，并开通了[黑心钻]的用户才能访问。
  
整体来说，以上的设计，配合监听器，无往不利！  
    信汪哥哥，得永生！ 

====================================================================================================================  

接下来，让我们一起来实际操练下。  
三、案例实操  
&nbsp;&nbsp;&nbsp;&nbsp;1、用户查询权限，才能访问xxx信息     
&nbsp;&nbsp;&nbsp;&nbsp;2、拥有[绿钻]或[黄钻]的用户，才能访问xxx信息     
&nbsp;&nbsp;&nbsp;&nbsp;3、12级会员才能访问xxx信息    
&nbsp;&nbsp;&nbsp;&nbsp;4、12级会员，以及12级会员以上，才能访问xxx信息       
&nbsp;&nbsp;&nbsp;&nbsp;5、用户身份证，只能自己看，和有指定权限的人可以看    
&nbsp;&nbsp;&nbsp;&nbsp;6、12级会员以上，并购买了12级会员专属vip礼包， 才能进入xxx副本        
&nbsp;&nbsp;&nbsp;&nbsp;7、直接开了包年，或购买了1月的A功能和B功能...   
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;包年=a功能+b功能+...后续的一些功能（动态，所以必定要查数据库）   
&nbsp;&nbsp;&nbsp;&nbsp;8、比如流行的 token=xxxx，通过校验后，才能访问   

    

#2.1 JWebPower框架，新增：

1、控制类下，方法名即是编号的功能（有开关，需要要打开开关，方支持）      
&nbsp;&nbsp;&nbsp;&nbsp;1.1 开关在系统对象类中的方法  methodIsCode()；返回true表示开启，false表示不开启（默认）    
&nbsp;&nbsp;&nbsp;&nbsp;1.2 当类、方法上没有任何的权限注解，也没有@JWPIgnoreCode注解（新增的注解，专门用来抵抗【方法名即编号】），那么，方法名即是编号         
	   
2、增加对接类的 视图路径收集方法（只对 把返回视图，用注解 标在方法或类上的 web框架 。比如 nutz)，视图路径的权限跟方法的一样    
&nbsp;&nbsp;&nbsp;&nbsp;2.1 当多个方法返回同一个视图时，那么这个视图跟谁的权限走？所以，这个功能，不建议用。除非你能确保，每个方法，都返回唯一的视图   
     
3、增加对接类的，对于【一个方法绑定多个请求路径】的支持   
&nbsp;&nbsp;&nbsp;&nbsp;3.1 即 方法：String[] getURLByMethod2(Method method)，默认是关闭的(返回null)   
&nbsp;&nbsp;&nbsp;&nbsp;3.2 使用此功能，必须把一个方法对一个路径的提取方法(String getURLByMethod)关闭（返回null)   
#功能调整   
1、当检验事件失败后，监听类返回true（默认）,方能触发 失败事件。   （上个版本，无论返回什么，都会执行失败事件）   




    