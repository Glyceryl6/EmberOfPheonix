Stream.of(
VoxelShapes.join(Block.box(4, 18, 4, 12, 26, 12), Block.box(3.5, 18, 3.5, 12.5, 27, 12.5), IBooleanFunction.HEAD),
Block.box(-2, 0, 3, 0, 17, 13),
Block.box(3, 0, 16, 13, 17, 18),
Block.box(16, 0, 3, 18, 17, 13),
Block.box(3, 0, -2, 13, 17, 0),
Block.box(6, -1, 6, 10, 17, 10)
).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();