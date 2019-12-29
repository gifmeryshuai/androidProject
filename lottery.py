```python

# coding=utf-8

import codecs
import math

from operator import itemgetter
# url = soup.find('a', class_='archive-title')


# print(url)

#选择彩票注数
import random


def select_num(lottery_type_str):
    lottery_num = input(lottery_type_str)
    try:
        lottery_num = int(lottery_num)
        if lottery_num <= 0:
            print('注数必须大于0')
            select_num(lottery_type_str)
        else:
            return int(lottery_num)
    except:
        print('输入有误请重新输入')
        select_num(lottery_type_str)

# 生成红色球
def create_red(red_res, red_num_all, red_num):
    temp = random.random()
    temp = red_num_all * temp + 1
    if int(temp) in red_res:
        create_red(red_res, red_num_all, red_num)
    else:
        red_res.append(int(temp))
    if len(red_res) < red_num:
        create_red(red_res, red_num_all, red_num)

def create_bule(bule_res, bule_num_all, bule_num):
    temp = random.random()  # [0, 1)
    temp = int(bule_num_all * temp) + 1
    if int(temp) in bule_res:
        create_bule(bule_res, bule_num_all, bule_num)
    else:
        bule_res.append(int(temp))
    if len(bule_res) < bule_num:
        create_bule(bule_res, bule_num_all, bule_num)

# 生成彩票结果

# 转成字符
def convert_ball_str(red_res, bule_res):
    red_res.sort(reverse=False)
    bule_res.sort(reverse=False)
    red_str = ''
    bule_str = ''
    for red in red_res:
        if red < 10:
            red_str += '0' + str(red)
        else:
            red_str += str(red)
        red_str += ' '

    for bule in bule_res:
        if bule < 10:
            bule_str += '0' + str(bule)
        else:
            bule_str += str(bule)
        bule_str += ' '
    ball_str = '红球:'+red_str+' '+'蓝球:'+bule_str
    return ball_str

def create_lottery(lottery_num, type):

    #“超级大乐透“35选5加12选2”投注方式多样
    #"双色球" 红色球号码从1--33中选择6个红色球；蓝色球号码从1--16中选择1个蓝色球。

    # 红色球集合

    while int(lottery_num) > 0:


        if type == 1:
            red_num = 5
            bule_num = 2
            red_num_all = 35
            bule_num_all = 12

        else:
            red_num = 6
            bule_num = 1
            red_num_all = 33
            bule_num_all = 16
        red_res = []
        bule_res = []
        create_red(red_res, red_num_all, red_num)
        create_bule(bule_res, bule_num_all, bule_num)
        ball_str = convert_ball_str(red_res, bule_res)
        print(ball_str)
        lottery_num = lottery_num - 1

def select_lottery():
    lottery_type = input('请选择彩票类型, 1.大乐透 2.双色球\n')
    try:
        if int(lottery_type) == 1:
            print('您选择的是大乐透\n')
            lottery_num = select_num('请输入大乐透的彩票注数\n')

            create_lottery(int(lottery_num), int(lottery_type))

        elif int(lottery_type) == 2:
            print('您选择的是双色球\n')
            lottery_num = select_num('请输入双色球的彩票注数\n')

            create_lottery(int(lottery_num), int(lottery_type))
        else:
            print('选择有误请重新选择')
            select_lottery()
    except:
        print('选择有误请重新选择')
        select_lottery()


if __name__ == '__main__':
    select_lottery()

    # print()


    # list1 = [2, 3, 512, 3, 654, 32, 54, 765, 23]
    #
    # list1.append("python")
    #
    # print(list1)



    # list2 = [32,'3aq', 'qedfe', 21, 44]

    # list3 = [(12, 'a3d', 11), (123, 'asdc', 345), (46, 'gd', 34), (45, '99', 678)]

    # 使用默认的List自带排序sort



    #使用自带的默认排序,以元素第一个进行比较
    # list1.sort(reverse=True)
    # l2 = list2.sort()
    #这个也是默认按照系统内置的排序方法,不过集合里面是tuple,元素存放的顺序要一致,否则没办法进行不同元素之间的比较
    # list3.sort(reverse=True)

    # print(list1)
    # 结果 [765, 654, 512, 54, 32, 23, 3, 3, 2]
    # print(l2)
    # print(list3)
    # 结果 [(123, 'asdc', 345), (46, 'gd', 34), (45, '99', 678), (12, 'a3d', 11)]


    # 使用参数key来指定某一元素进行比较, 使用的是匿名表达式
    # 一元素中tuple的第二的元素作为比较对象元素进行排序,并且倒序显示排列
    # list3.sort(key=lambda x:(x[1]), reverse=True)
    # print(list3)
    # 结果 [(46, 'gd', 34), (123, 'asdc', 345), (12, 'a3d', 11), (45, '99', 678)]

    # 一元素中tuple的第一的元素作为比较对象元素进行排序,并且倒序显示排列  这个就是跟系统默认排序一致结果
    # list3.sort(key=lambda x: (x[0]), reverse=True)
    # print(list3)
    # 结果 [(123, 'asdc', 345), (46, 'gd', 34), (45, '99', 678), (12, 'a3d', 11)]

    # 一元素中tuple的第三的元素作为比较对象元素进行排序,并且倒序显示排列
    # list3.sort(key=lambda x: (x[2]), reverse=False)
    # print(list3)
    # 结果 [(12, 'a3d', 11), (46, 'gd', 34), (123, 'asdc', 345), (45, '99', 678)]


    # 使用匿名表达式进行排序运算,其实可以传递多个元素进行,当第一个元素比较的过程中遇到相同的,可以使用第二个进行再次排序计算
    # list3.sort(key= lambda x:(x[0], x[1]))
    # print(list3)

    # 另外还可以使用operator模块中的itemgetter方法函数进行重写key所代表的函数,按照下标为0的顺序排列
    # list3.sort(key=itemgetter(0))
    # print(list3)
    # 结果 [(12, 'a3d', 11), (45, '99', 678), (46, 'gd', 34), (123, 'asdc', 345)]


    # 另外还可以使用operator模块中的itemgetter方法函数进行重写key所代表的函数,按照下标为1的顺序排列
    # list3.sort(key=itemgetter(1))
    # print(list3)
    # 结果 [(45, '99', 678), (12, 'a3d', 11), (123, 'asdc', 345), (46, 'gd', 34)]

    # 同时,itemgetter和lambda一样,也可以同时传递多个参数元素进行,逐一比较,如果第一个元素比较是相同的,那么就比较第二个
    # list3.sort(key=itemgetter(0,1), reverse=True)
    # print(list3)



    # 结果 [(12, 'a3d', 11), (45, '99', 678), (46, 'gd', 34), (123, 'asdc', 345)]

    # list1 = [(2, 'huan', 23), (23, 'liu', 90), (12, 'the', 14), (15, 'the', 14)]
    # list1.sort()
    #
    # print(list1)
    #
    # # 使用匿名表达式重写key所表示的函数
    #
    # list1.sort(key= lambda x:(x[2]))
    #
    # print(list1)
    #
    # list1.sort(key=lambda x:x[1])
    #
    # print(list1)
    #
    #
    # list1.sort(key=lambda x:(x[2], x[0]))
    #
    # print(list1)
    #
    # list1.sort(key=lambda x: (x[0], x[2]))

    # print(list1)
    # 我们都知道Python内置函数abs是求绝对值的

    # print(abs(-1))
    # 得到的结果是1

    # 如果我们把abs函数赋给一个变量

    # f = abs(-1)
    #
    # print(f)

    # 而结果还是1


    # 再举个例子, 如果我们定义一个函数,里面需要三个参数x,y,z,其中
    # x,y是普通参数,z是函数参数

    # def par(r):
        # return abs(r)
    # def funpar(x, y , z):
        # print("开始")
        # s = z(x) + z(y)
        # print(s)
    # funpar(-10, 4, par)


    # L = [1,2,3,4,5]
    #
    # def par(x):
    #
    #     return x+x
    #
    # L = map(par, L)
    #
    # print(L.__next__())
    #
    # for l in L:
    #     print(l)

    # L = [1,2,3,4,5,6,7,8,9,10]
    #
    # def ft(x):
    #     return x % 2 == 0


    # L = filter(ft, L)

    # for d in L:
    #     print(d)



    # L = range(1, 101)
    # print(L)
    # for d in L:
    #     print(d)

    # sorted Python高阶函数排序

    # def reversed_cmp(x, y):
    #     if x > y:
    #         return -1
    #     if x < y:
    #         return 1
    #     return 0
    #
    # l = [2,5,3,7,1,9,5]
    #
    #
    # b = l.sort()
    # print(b)
    # b = sorted(l, reverse=True)

    # b = sorted(l)
    # print(b)


    # 其实函数还可以指向函数

    # abs  = len

    # 当这个条件成立之后 abs就已经丧失abs绝对值的功能,拥有了len函数的功能

    # print(len([1,2,3,4,5,6]))

    # fd = codecs.open('C:/Users/acer/Desktop/Untitled1.txt', 'r', encoding='utf-8')
    # con = fd.read()
    # # print(con)
    # fd.close()
    # # content = '<html><head><title></title></head><body><div class="post floated-thumb">< !-- BEGIN.post - thumb -->< div class ="post-thumb" ><a target = "_blank" href = "http://python.jobbole.com/89012/" title = "实现属于自己的TensorFlow(二) - 梯度计算与反向传播" > <img src = "http://pytlab.org/assets/images/blog_img/2018-01-24-%E5%AE%9E%E7%8E%B0%E5%B1%9E%E4%BA%8E%E8%87%AA%E5%B7%B1%E7%9A%84TensorFlow-%E4%B8%80-%E8%AE%A1%E7%AE%97%E5%9B%BE%E4%B8%8E%E5%89%8D%E5%90%91%E4%BC%A0%E6%92%AD/feature.png" alt = "" width = "120" height = "120" / > </a>  </div> <!-- END.post - thumb --> <!-- BEGIN.post - meta --> <div class ="post-meta"> <p> <a class ="archive-title" target="_blank" href="http://python.jobbole.com/89012/" title="实现属于自己的TensorFlow(二) - 梯度计算与反向传播"> 实现属于自己的TensorFlow(二) - 梯度计算与反向传播 </a> <br/> 2018 / 02 / 04 & middot; <a href = "http://python.jobbole.com/category/project/" rel = "category tag"> 实践项目 </a> </p><span class ="excerpt"> <p> 上一篇中介绍了计算图以及前向传播的实现，本文中将主要介绍对于模型优化非常重要的反向传播算法以及反向传播算法中梯度计算的实现。因为在计算梯度的时候需要涉及到矩阵梯度的计算，本文针对几种常用操作的梯度计算和实现进行了较为详细的介绍。如有错误欢迎指出。 </p> </span> <p class ="align-right"> <span class ="read-more"> <a target="blank" href="http://python.jobbole.com/89012/" > 阅读全文 » </a> </span> </p></div><!-- END.post - meta --><div class ="clear"> </div></div> </body></html>'
    # soup = BeautifulSoup(con, "html.parser")
    #
    #
    # divs = soup.find_all('div', class_='post floated-thumb')
    #
    # for div in divs:
    #     pos = div.find_all(name='div')
    #
    #     for po in pos:
    #         va = po.find(name='a')
    #         ass = po.children
    #         for cl in ass:
    #
    #             print(cl)
            # name = ass.get('name')
            # title = ass['title']
            # print(title)
            # href = va.get('href')
            # title = va['title']
            # print(va)
        # print(div)

    # path = soup.find('div', class_='post-thumb').find(name='a').get('href')
    # print(path)




    # divs = soup.div

    # div = soup.find_all('div', class_='post floated-thumb')

    # test = div[0]
    # print(test.context)
    # for d in div:
    #     print(d)
        # so = BeautifulSoup(d, 'html.parser')



        # print()
    # d = div.children()
    # print(d)

    # print(soup.find_all('a'))


```
