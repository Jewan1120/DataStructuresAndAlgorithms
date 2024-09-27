public class MyUnionByRank {
    private int[] parent;
    private int[] rank;

    public MyUnionByRank(int size) {
        // Union By Rank and Path Compression
        parent = new int[size];
        rank = new int[size];

        // 초기화 : 각 원소가 자신이 속한 집합의 대표를 자신으로 설정
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // Find연산 : 원소가 속한 집합의 대표를 찾는 연산
    public int find(int x) {
        if (parent[x] != x) {
            // 재귀호출과 경로 압축(Path Compression)을 통한 최적화
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // Union연산 : 두 원소가 속한 집합을 합치는 연산
    public void unionByRank(int x, int y) {
        int rootX = find(x); // 원소 x의 대표
        int rootY = find(y); // 원소 y의 대표

        // 두 집합의 대표가 같으면 이미 같은 집합
        if (rootX != rootY) {
            // 랭크가 작은 쪽을 더 큰 쪽에 붙임
            // 랭크가 작다 = 트리의 높이가 높다
            // 랭크가 크다 = 트리의 높이가 낮다
            // 즉, 트리의 높이가 낮은 곳으로 합침
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                // 양쪽 랭크가 같은 경우에는 반대쪽을 랭크를 증가
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

    public static void main(String[] args) {
        // 초기화
        MyUnionByRank myUnionFind = new MyUnionByRank(11); // [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

        // Find -> Union
        myUnionFind.unionByRank(0, 1); // [0, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10]
        myUnionFind.unionByRank(1, 2); // [0, 0, 0, 3, 4, 5, 6, 7, 8, 9, 10]
        myUnionFind.unionByRank(2, 3); // [0, 0, 0, 0, 4, 5, 6, 7, 8, 9, 10]
        myUnionFind.unionByRank(3, 4); // [0, 0, 0, 0, 0, 5, 6, 7, 8, 9, 10]
        myUnionFind.unionByRank(4, 5); // [0, 0, 0, 0, 0, 0, 6, 7, 8, 9, 10]
        myUnionFind.unionByRank(6, 7); // [0, 0, 0, 0, 0, 0, 6, 6, 8, 9, 10]
        myUnionFind.unionByRank(7, 8); // [0, 0, 0, 0, 0, 0, 6, 6, 6, 9, 10]
        myUnionFind.unionByRank(8, 9); // [0, 0, 0, 0, 0, 0, 6, 6, 6, 6, 10]
        myUnionFind.unionByRank(9, 10); // [0, 0, 0, 0, 0, 0, 6, 6, 6, 6, 6]
        
        // 1과 5가 같은 집합인지 확인
        System.out.println(myUnionFind.find(1) == myUnionFind.find(5)); // true
        
        // 5와 6이 같은 집합인지 확인
        System.out.println(myUnionFind.find(5) == myUnionFind.find(6)); // false
    }
}
