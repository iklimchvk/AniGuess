package AnGuess;
import java.util.Scanner;

public class AnGuess {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        BaseNode<String> root;
        Start();
        root = baseTree();
        do
            Play(root);
        while (Query("Еще сыграем?"));

        System.out.println("Спасибо за игру");
    }

    public static void Start()
    {
        System.out.println("Загадай животное.");
    }

    public static void Play(BaseNode<String> current)
    {
        while (!current.endNode())
        {
            if (Query(current.getData()))
                current = current.getLeft();
            else
                current = current.getRight();
        }

        System.out.print("Я думаю это " + current.getData() + ". " );
        if (!Query("Я угадал?"))
            Learn(current);
        else
            System.out.println("Я знал это:)");
    }

    public static BaseNode<String> baseTree()
    {
        BaseNode<String> root;
        BaseNode<String> child;

        final String rootQuest = "Твое животное обитает на суше?";
        final String Animal1 = "Кот";
        final String Animal2 = "Кит";

        root = new BaseNode<String>(rootQuest, null, null);
        root.setLeft(new BaseNode<String>(Animal1, null, null));
        root.setRight(new BaseNode<String>(Animal2, null, null));

        return root;
    }

    public static void Learn(BaseNode<String> current)
    {
        String guessAnimal;
        String correctAnimal;
        String newQuestion;

        guessAnimal = current.getData( );
        System.out.println("Я не угадал, так кто же твое животное?");
        correctAnimal = scanner.nextLine( );
        System.out.println("Введи вопрос который будет отличать твое животное -");
        System.out.println("Чем животное '" + correctAnimal + "' отличается от животного '" + guessAnimal + "'.");
        newQuestion = scanner.nextLine( );

        current.setData(newQuestion);
        System.out.println("В итоге " + correctAnimal + ", " + newQuestion);
        if (Query("Нужен твой ответ 'Y/N'"))
        {
            current.setLeft(new BaseNode<String>(correctAnimal, null, null));
            current.setRight(new BaseNode<String>(guessAnimal, null, null));
        }
        else
        {
            current.setLeft(new BaseNode<String>(guessAnimal, null, null));
            current.setRight(new BaseNode<String>(correctAnimal, null, null));
        }
    }

    public static boolean Query(String prompt)
    {
        String answer;

        System.out.print(prompt + " [Y or N]: ");
        answer = scanner.nextLine( ).toUpperCase( );
        while (!answer.startsWith("Y") && !answer.startsWith("N"))
        {
            System.out.print("Неверный ответ, нужно ввести символ 'Y' или 'N': ");
            answer = scanner.nextLine( ).toUpperCase( );
        }

        return answer.startsWith("Y");
    }

    public static class BaseNode<E>
    {
        private E data;
        private BaseNode<E> left, right;

        public BaseNode(E initialData, BaseNode<E> initialLeft, BaseNode<E> initialRight)
        {
            data = initialData;
            left = initialLeft;
            right = initialRight;
        }

        public E getData()
        {
            return data;
        }

        public BaseNode<E> getLeft()
        {
            return left;
        }

        public E getLeftmostData()
        {
            if (left == null)
                return data;
            else
                return left.getLeftmostData();
        }

        public BaseNode<E> getRight()
        {
            return right;
        }

        public E getRightmostData()
        {
            if (left == null)
                return data;
            else
                return left.getRightmostData();
        }

        public boolean endNode()
        {
            return (left == null) && (right == null);
        }

        public void preNodeOutput()
        {
            System.out.println(data);
            if (left != null)
                left.preNodeOutput();
            if (right != null)
                right.preNodeOutput();
        }

        public void postNodeOutput()
        {
            if (left != null)
                left.postNodeOutput();
            if (right != null)
                right.postNodeOutput();
            System.out.println(data);
        }

        public void inNodeOutput()
        {
            if (left != null)
                left.inNodeOutput();
            System.out.println(data);
            if (right != null)
                right.inNodeOutput();
        }

        public void print(int depth)
        {
            int i;
            for (i = 1; i <= depth; i++)
                System.out.print("    ");
            System.out.println(data);

            if (left != null)
                left.print(depth+1);
            else if (right != null)
            {
                for (i = 1; i <= depth+1; i++)
                    System.out.print("    ");
                System.out.println("--");
            }

            if (right != null)
                right.print(depth+1);
            else if (left != null)
            {
                for (i = 1; i <= depth+1; i++)
                    System.out.print("    ");
                System.out.println("--");
            }
        }

        public BaseNode<E> removeLeftmost()
        {
            if (left == null)
                return right;
            else
            {
                left = left.removeLeftmost();
                return this;
            }
        }

        public BaseNode<E> removeRightmost()
        {
            if (right == null)
                return left;
            else
            {
                right = right.removeRightmost();
                return this;
            }
        }

        public void setData(E newData)
        {
            data = newData;
        }

        public void setLeft(BaseNode<E> newLeft)
        {
            left = newLeft;
        }

        public void setRight(BaseNode<E> newRight)
        {
            right = newRight;
        }

        public static <E> BaseNode<E> treeCopy(BaseNode<E> source)
        {
            BaseNode<E> leftCopy, rightCopy;

            if (source == null)
                return null;
            else
            {
                leftCopy = treeCopy(source.left);
                rightCopy = treeCopy(source.right);
                return new BaseNode<E>(source.data, leftCopy, rightCopy);
            }
        }

        public static <E> long treeSize(BaseNode<E> root)
        {
            if (root == null)
                return 0;
            else
                return 1 + treeSize(root.left) + treeSize(root.right);
        }
    }
}

