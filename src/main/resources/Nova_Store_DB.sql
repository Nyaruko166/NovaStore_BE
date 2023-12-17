USE
[master]
GO
/****** Object:  Database [Nova_Store_DB]    Update: 25/11/2023 00:31 AM ******/
CREATE
DATABASE [Nova_Store_DB]
GO
USE [Nova_Store_DB]
IF (1 = FULLTEXTSERVICEPROPERTY(''IsFullTextInstalled''))
begin
EXEC [Nova_Store_DB].[dbo].[sp_fulltext_database] @action = ''enable''
end
GO
ALTER
DATABASE [Nova_Store_DB] SET ANSI_NULL_DEFAULT OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET ANSI_NULLS OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET ANSI_PADDING OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET ANSI_WARNINGS OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET ARITHABORT OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET AUTO_CLOSE ON
GO
ALTER
DATABASE [Nova_Store_DB] SET AUTO_SHRINK OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER
DATABASE [Nova_Store_DB] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER
DATABASE [Nova_Store_DB] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET NUMERIC_ROUNDABORT OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET QUOTED_IDENTIFIER OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET RECURSIVE_TRIGGERS OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET  ENABLE_BROKER
GO
ALTER
DATABASE [Nova_Store_DB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET TRUSTWORTHY OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET PARAMETERIZATION SIMPLE
GO
ALTER
DATABASE [Nova_Store_DB] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET RECOVERY SIMPLE
GO
ALTER
DATABASE [Nova_Store_DB] SET  MULTI_USER
GO
ALTER
DATABASE [Nova_Store_DB] SET PAGE_VERIFY CHECKSUM
GO
ALTER
DATABASE [Nova_Store_DB] SET DB_CHAINING OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF )
GO
ALTER
DATABASE [Nova_Store_DB] SET TARGET_RECOVERY_TIME = 60 SECONDS
GO
ALTER
DATABASE [Nova_Store_DB] SET DELAYED_DURABILITY = DISABLED
GO
ALTER
DATABASE [Nova_Store_DB] SET ACCELERATED_DATABASE_RECOVERY = OFF
GO
ALTER
DATABASE [Nova_Store_DB] SET QUERY_STORE = ON
GO
ALTER
DATABASE [Nova_Store_DB] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [Nova_Store_DB]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 10/10/2023 9:34:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Name] [nvarchar]
(
    50
) NULL,
    [Email] [varchar]
(
    50
) NULL,
    [PhoneNumber] [varchar]
(
    15
) NULL,
    [Password] [varchar]
(
    255
) NULL,
    [Birthday] [datetime] NULL,
    [Avatar] [varchar]
(
    255
) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [RoleId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Address]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[Address]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [CustomerName] nvarchar
(
    50
) NULL,
    [PhoneNumber] varchar
(
    15
) NULL,
    [SpecificAddress] [nvarchar]
(
    50
) NULL,
    [Ward] [nvarchar]
(
    50
) NULL,
    [District] [nvarchar]
(
    50
) NULL,
    [City] [nvarchar]
(
    50
) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [AccountId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Bill]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[Bill]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Code] [varchar]
(
    50
) NULL,
    [Type] [int] NULL,
    [CustomerName] [nvarchar]
(
    50
) NULL,
    [Address] [nvarchar]
(
    100
) NULL,
    [PhoneNumber] [nvarchar]
(
    15
) NULL,
    [Note] [nvarchar]
(
    255
) NULL,
    [OrderDate] [datetime] NULL,
    [PaymentDate] [datetime] NULL,
    [ConfirmationDate] [datetime] NULL,
    [ShippingDate] [datetime] NULL,
    [CompletionDate] [datetime] NULL,
    [CancellationDate] [datetime] NULL,
    [ShippingFee] [money] NULL,
    [Price] [money] NULL,
    [DiscountAmount] [money] NULL,
    [TotalPrice] [money] NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [CustomerId] [int] NULL,
    [VoucherId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[BillDetail]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[BillDetail]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Price] [money] NULL,
    [Quantity] [int] NULL,
    [Status] [int] NULL,
    [BillId] [int] NULL,
    [ProductDetailId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Brand]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[Brand]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Code] [varchar]
(
    50
) NULL,
    [Name] [nvarchar]
(
    50
) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Cart]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[Cart]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [TotalPrice] [money] NULL,
    [TotalItems] [int] NULL,
    [AccountId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[CartDetail]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[CartDetail]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Price] [money] NULL,
    [Quantity] [int] NULL,
    [CartId] [int] NULL,
    [ProductDetailId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Category]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[Category]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Code] [varchar]
(
    50
) NULL,
    [Name] [nvarchar]
(
    50
) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Color]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[Color]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Code] [varchar]
(
    50
) NULL,
    [Name] [nvarchar]
(
    50
) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Form]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[Form]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Code] [varchar]
(
    50
) NULL,
    [Name] [nvarchar]
(
    50
) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Image]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[Image]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Name] [varchar]
(
    255
) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [ProductId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Material]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[Material]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Code] [varchar]
(
    50
) NULL,
    [Name] [nvarchar]
(
    50
) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[PaymentMethod]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[PaymentMethod]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Name] [nvarchar]
(
    50
) NULL,
    [Money] [money] NULL,
    [Description] [nvarchar]
(
    50
) NULL,
    [Status] [int] NULL,
    [BillId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Product]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[Product]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Code] [varchar]
(
    50
) UNIQUE,
    [Name] [nvarchar]
(
    50
) NULL,
    [Description] [nvarchar]
(
    255
) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [BrandId] [int] NULL,
    [CategoryId] [int] NULL,
    [FormId] [int] NULL,
    [MaterialId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[ProductDetail]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[ProductDetail]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Code] [varchar]
(
    50
) UNIQUE,
    [Quantity] [int] NULL,
    [Price] [money] NULL,
    [PriceDiscount] [money] NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [ProductId] [int] NULL,
    [SizeId] [int] NULL,
    [ColorId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Promotion]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[Promotion]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Code] [varchar]
(
    255
) NULL,
    [Name] [nvarchar]
(
    50
) NULL,
    [Value] [float] NULL,
    [StartDate] [datetime] NULL,
    [EndDate] [datetime] NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[PromotionDetail]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[PromotionDetail]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [ProductId] [int] NULL,
    [PromotionId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Role]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[Role]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Name] [nvarchar]
(
    50
) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Size]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[Size]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Code] [varchar]
(
    50
) NULL,
    [Name] [nvarchar]
(
    50
) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Voucher]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS
    ON
    GO
    SET QUOTED_IDENTIFIER
    ON
    GO
CREATE TABLE [dbo].[Voucher]
(
    [
    Id] [
    int]
    IDENTITY
(
    1,
    1
) NOT NULL,
    [Code] [varchar]
(
    50
) NULL,
    [Name] [nvarchar]
(
    50
) NULL,
    [Value] [money] NULL,
    [Quantity] [int] NULL,
    [MinimumPrice] [money] NULL,
    [StartDate] [datetime] NULL,
    [EndDate] [datetime] NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[
    Id] ASC
)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF)
    ON [PRIMARY]
    )
    ON [PRIMARY]
    GO
ALTER TABLE [dbo].[Account] WITH CHECK ADD CONSTRAINT [FKi9xmahyh65di7x2wn5fvt8lv3] FOREIGN KEY ([RoleId])
    REFERENCES [dbo].[Role] ([Id])
    GO
ALTER TABLE [dbo].[Account] CHECK CONSTRAINT [FKi9xmahyh65di7x2wn5fvt8lv3]
    GO
ALTER TABLE [dbo].[Address] WITH CHECK ADD CONSTRAINT [FK3haj5uqn2j6ar58mcglooa5bp] FOREIGN KEY ([AccountId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[Address] CHECK CONSTRAINT [FK3haj5uqn2j6ar58mcglooa5bp]
    GO
ALTER TABLE [dbo].[Bill] WITH CHECK ADD CONSTRAINT [FK5mrre5s0gacpqu6737kfocwkl] FOREIGN KEY ([CustomerId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[Bill] CHECK CONSTRAINT [FK5mrre5s0gacpqu6737kfocwkl]
    GO
ALTER TABLE [dbo].[Bill] WITH CHECK ADD CONSTRAINT [FK2hf3g6padqdy15tccpshmpxob] FOREIGN KEY ([VoucherId])
    REFERENCES [dbo].[Voucher] ([Id])
    GO
ALTER TABLE [dbo].[Bill] CHECK CONSTRAINT [FK2hf3g6padqdy15tccpshmpxob]
    GO
ALTER TABLE [dbo].[BillDetail] WITH CHECK ADD CONSTRAINT [FK8sw1tfhht3q5xtdsyoe0r7jfd] FOREIGN KEY ([BillId])
    REFERENCES [dbo].[Bill] ([Id])
    GO
ALTER TABLE [dbo].[BillDetail] CHECK CONSTRAINT [FK8sw1tfhht3q5xtdsyoe0r7jfd]
    GO
ALTER TABLE [dbo].[BillDetail] WITH CHECK ADD CONSTRAINT [FKnt7lacod5l24jdnfgxfydqiu2] FOREIGN KEY ([ProductDetailId])
    REFERENCES [dbo].[ProductDetail] ([Id])
    GO
ALTER TABLE [dbo].[BillDetail] CHECK CONSTRAINT [FKnt7lacod5l24jdnfgxfydqiu2]
    GO
ALTER TABLE [dbo].[Cart] WITH CHECK ADD CONSTRAINT [FK1w1km3ww10t0maawf2cymyx5i] FOREIGN KEY ([AccountId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[Cart] CHECK CONSTRAINT [FK1w1km3ww10t0maawf2cymyx5i]
    GO
ALTER TABLE [dbo].[CartDetail] WITH CHECK ADD CONSTRAINT [FK6unelr9lsy26gw9da5tuuxcsh] FOREIGN KEY ([CartId])
    REFERENCES [dbo].[Cart] ([Id])
    GO
ALTER TABLE [dbo].[CartDetail] CHECK CONSTRAINT [FK6unelr9lsy26gw9da5tuuxcsh]
    GO
ALTER TABLE [dbo].[CartDetail] WITH CHECK ADD CONSTRAINT [FKepuvpwbykpahqt0gagvvdqoyn] FOREIGN KEY ([ProductDetailId])
    REFERENCES [dbo].[ProductDetail] ([Id])
    GO
ALTER TABLE [dbo].[CartDetail] CHECK CONSTRAINT [FKepuvpwbykpahqt0gagvvdqoyn]
    GO
ALTER TABLE [dbo].[Image] WITH CHECK ADD CONSTRAINT [FKdtaisglfgjjj5j1a7g3fcev7c] FOREIGN KEY ([ProductId])
    REFERENCES [dbo].[Product] ([Id])
    GO
ALTER TABLE [dbo].[Image] CHECK CONSTRAINT [FKdtaisglfgjjj5j1a7g3fcev7c]
    GO
ALTER TABLE [dbo].[PaymentMethod] WITH CHECK ADD CONSTRAINT [FKjity6x6p1194mtoli4abb3jgc] FOREIGN KEY ([BillId])
    REFERENCES [dbo].[Bill] ([Id])
    GO
ALTER TABLE [dbo].[PaymentMethod] CHECK CONSTRAINT [FKjity6x6p1194mtoli4abb3jgc]
    GO
ALTER TABLE [dbo].[Product] WITH CHECK ADD CONSTRAINT [FK4cx1ir1xlnytlte2quullny9m] FOREIGN KEY ([FormId])
    REFERENCES [dbo].[Form] ([Id])
    GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK4cx1ir1xlnytlte2quullny9m]
    GO
ALTER TABLE [dbo].[Product] WITH CHECK ADD CONSTRAINT [FK6pnobu31k3yhhmk45s97imkui] FOREIGN KEY ([CategoryId])
    REFERENCES [dbo].[Category] ([Id])
    GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK6pnobu31k3yhhmk45s97imkui]
    GO
ALTER TABLE [dbo].[Product] WITH CHECK ADD CONSTRAINT [FKfc8uiunvrolmn3qa9ahrhmtrw] FOREIGN KEY ([MaterialId])
    REFERENCES [dbo].[Material] ([Id])
    GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FKfc8uiunvrolmn3qa9ahrhmtrw]
    GO
ALTER TABLE [dbo].[Product] WITH CHECK ADD CONSTRAINT [FKnuw1iwpj73v904j79uc8qurgc] FOREIGN KEY ([BrandId])
    REFERENCES [dbo].[Brand] ([Id])
    GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FKnuw1iwpj73v904j79uc8qurgc]
    GO
ALTER TABLE [dbo].[ProductDetail] WITH CHECK ADD CONSTRAINT [FKik38y3bjry9u05majdn5u3egj] FOREIGN KEY ([ProductId])
    REFERENCES [dbo].[Product] ([Id])
    GO
ALTER TABLE [dbo].[ProductDetail] CHECK CONSTRAINT [FKik38y3bjry9u05majdn5u3egj]
    GO
ALTER TABLE [dbo].[ProductDetail] WITH CHECK ADD CONSTRAINT [FK61xkqx42jtcc8we64hahp05pv] FOREIGN KEY ([SizeId])
    REFERENCES [dbo].[Size] ([Id])
    GO
ALTER TABLE [dbo].[ProductDetail] CHECK CONSTRAINT [FK61xkqx42jtcc8we64hahp05pv]
    GO
ALTER TABLE [dbo].[ProductDetail] WITH CHECK ADD CONSTRAINT [FKntlsi9s4irkogtc9mbw03s90y] FOREIGN KEY ([ColorId])
    REFERENCES [dbo].[Color] ([Id])
    GO
ALTER TABLE [dbo].[ProductDetail] CHECK CONSTRAINT [FKntlsi9s4irkogtc9mbw03s90y]
    GO
ALTER TABLE [dbo].[PromotionDetail] WITH CHECK ADD CONSTRAINT [FKa83ktjk5axkasy5c9v2s1ukig] FOREIGN KEY ([ProductId])
    REFERENCES [dbo].[Product] ([Id])
    GO
ALTER TABLE [dbo].[PromotionDetail] CHECK CONSTRAINT [FKa83ktjk5axkasy5c9v2s1ukig]
    GO
ALTER TABLE [dbo].[PromotionDetail] WITH CHECK ADD CONSTRAINT [FKos6ftbatvw4mk81km7xpkds5i] FOREIGN KEY ([PromotionId])
    REFERENCES [dbo].[Promotion] ([Id])
    GO
ALTER TABLE [dbo].[PromotionDetail] CHECK CONSTRAINT [FKos6ftbatvw4mk81km7xpkds5i]
    GO
    USE [master]
    GO
ALTER
DATABASE [Nova_Store_DB] SET  READ_WRITE
GO

USE Nova_Store_DB

SELECT *
FROM Brand
    INSERT
INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('' TH0001 '', N'' Reebok '', GETDATE(), GETDATE(), 1)
INSERT
INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('' TH0002 '', N'' Asics '', GETDATE(), GETDATE(), 1)
INSERT
INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('' TH0003 '', N'' Brooks '', GETDATE(), GETDATE(), 1)
INSERT
INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('' TH0004 '', N'' Mizuno '', GETDATE(), GETDATE(), 1)
INSERT
INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('' TH0005 '', N'' New balance '', GETDATE(), GETDATE(), 1)
INSERT
INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('' TH0006 '', N'' Puma '', GETDATE(), GETDATE(), 1)
INSERT
INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('' TH0007 '', N'' Nike '', GETDATE(), GETDATE(), 1)
INSERT
INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('' TH0008 '', N'' Skechers '', GETDATE(), GETDATE(), 1)
INSERT
INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('' TH0009 '', N'' Under armour '', GETDATE(), GETDATE(), 1)
INSERT
INTO Brand (code, name, CreateDate, UpdateDate, Status)
VALUES ('' TH0010 '', N'' Adidas '', GETDATE(), GETDATE(), 1)


SELECT *
FROM Category
    INSERT
INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('' L0001 '', N'' Áo thun '', GETDATE(), GETDATE(), 1)
INSERT
INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('' L0002 '', N'' Áo khoác '', GETDATE(), GETDATE(), 1)
INSERT
INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('' L0003 '', N'' Áo ngắn tay '', GETDATE(), GETDATE(), 1)
INSERT
INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('' L0004 '', N'' Áo Polo '', GETDATE(), GETDATE(), 1)
INSERT
INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('' L0005 '', N'' Áo dài tay '', GETDATE(), GETDATE(), 1)
INSERT
INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('' L0006 '', N'' Quần bó '', GETDATE(), GETDATE(), 1)
INSERT
INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('' L0007 '', N'' Quần cạp cao '', GETDATE(), GETDATE(), 1)
INSERT
INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('' L0008 '', N'' Quần đùi '', GETDATE(), GETDATE(), 1)
INSERT
INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('' L0009 '', N'' Quần dài '', GETDATE(), GETDATE(), 1)
INSERT
INTO Category (code, name, CreateDate, UpdateDate, Status)
VALUES ('' L0010 '', N'' Quần legging '', GETDATE(), GETDATE(), 1)

SELECT *
FROM Material
    INSERT
INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('' CL0001 '', N'' Spandex '', GETDATE(), GETDATE(), 1)
INSERT
INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('' CL0002 '', N'' Cotton '', GETDATE(), GETDATE(), 1)
INSERT
INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('' CL0003 '', N'' Mesh '', GETDATE(), GETDATE(), 1)
INSERT
INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('' CL0004 '', N'' Nylon '', GETDATE(), GETDATE(), 1)
INSERT
INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('' CL0005 '', N'' Viscose '', GETDATE(), GETDATE(), 1)
INSERT
INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('' CL0006 '', N'' Microfiber '', GETDATE(), GETDATE(), 1)
INSERT
INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('' CL0007 '', N'' Linen '', GETDATE(), GETDATE(), 1)
INSERT
INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('' CL0008 '', N'' Syntheic '', GETDATE(), GETDATE(), 1)
INSERT
INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('' CL0009 '', N'' Polyester '', GETDATE(), GETDATE(), 1)
INSERT
INTO Material (code, name, CreateDate, UpdateDate, Status)
VALUES ('' CL0010 '', N'' Fleece '', GETDATE(), GETDATE(), 1)


SELECT *
FROM Form
    INSERT
INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('' KD0001 '', N'' Bó '', GETDATE(), GETDATE(), 1)
INSERT
INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('' KD0002 '', N'' Rộng '', GETDATE(), GETDATE(), 1)
INSERT
INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('' KD0003 '', N'' Tank top '', GETDATE(), GETDATE(), 1)
INSERT
INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('' KD0004 '', N'' Jogger '', GETDATE(), GETDATE(), 1)
INSERT
INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('' KD0005 '', N'' Dài '', GETDATE(), GETDATE(), 1)
INSERT
INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('' KD0006 '', N'' Ngắn '', GETDATE(), GETDATE(), 1)
INSERT
INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('' KD0007 '', N'' Dày '', GETDATE(), GETDATE(), 1)
INSERT
INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('' KD0008 '', N'' Mỏng '', GETDATE(), GETDATE(), 1)
INSERT
INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('' KD0009 '', N'' Lưới '', GETDATE(), GETDATE(), 1)
INSERT
INTO Form (code, name, CreateDate, UpdateDate, Status)
VALUES ('' KD0010 '', N'' Thiếu vải '', GETDATE(), GETDATE(), 1)


SELECT *
FROM Size
    INSERT
INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('' S0001 '', N''28'', GETDATE(), GETDATE(), 1)
INSERT
INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('' S0002 '', N''29'', GETDATE(), GETDATE(), 1)
INSERT
INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('' S0003 '', N''30'', GETDATE(), GETDATE(), 1)
INSERT
INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('' S0004 '', N''31'', GETDATE(), GETDATE(), 1)
INSERT
INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('' S0005 '', N''32'', GETDATE(), GETDATE(), 1)
INSERT
INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('' S0006 '', N'' XXL '', GETDATE(), GETDATE(), 1)
INSERT
INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('' S0007 '', N'' XL '', GETDATE(), GETDATE(), 1)
INSERT
INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('' S0008 '', N'' L '', GETDATE(), GETDATE(), 1)
INSERT
INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('' S0009 '', N'' S '', GETDATE(), GETDATE(), 1)
INSERT
INTO Size (code, name, CreateDate, UpdateDate, Status)
VALUES ('' S0010 '', N'' M '', GETDATE(), GETDATE(), 1)


SELECT *
FROM Color
    INSERT
INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('' M0001 '', N'' Đen '', GETDATE(), GETDATE(), 1)
INSERT
INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('' M0002 '', N'' Đỏ '', GETDATE(), GETDATE(), 1)
INSERT
INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('' M0003 '', N'' Xanh dương '', GETDATE(), GETDATE(), 1)
INSERT
INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('' M0004 '', N'' Xanh lá '', GETDATE(), GETDATE(), 1)
INSERT
INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('' M0005 '', N'' Vàng '', GETDATE(), GETDATE(), 1)
INSERT
INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('' M0006 '', N'' Trắng '', GETDATE(), GETDATE(), 1)
INSERT
INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('' M0007 '', N'' Xám '', GETDATE(), GETDATE(), 1)
INSERT
INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('' M0008 '', N'' Ghi '', GETDATE(), GETDATE(), 1)
INSERT
INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('' M0009 '', N'' Nâu '', GETDATE(), GETDATE(), 1)
INSERT
INTO Color (code, name, CreateDate, UpdateDate, Status)
VALUES ('' M0010 '', N'' Cam '', GETDATE(), GETDATE(), 1)


SELECT *
FROM Product
    INSERT
INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('' SP00001 '', N'' Áo Thun Thể Thao Nam Tay Ngắn Rã Vai Form Regular '', N'' vip pro '', GETDATE(), GETDATE(), 1, 1, 1, 1, 1);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00002'', N''Áo khoác Flannel tay dài kháo kéo'', N''Chiến thần'', GETDATE(), GETDATE(), 1, 2, 2, 2, 2);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00003'', N''Áo khoác bomber Nylon'', N''quá là áo'', GETDATE(), GETDATE(), 1, 3, 3, 3, 3);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00004'', N''Áo len Polo'', N''vip plus'', GETDATE(), GETDATE(), 1, 4, 4, 4, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00005'', N''Áo Gile len'', N''trên cả tuyệt vời'', GETDATE(), GETDATE(), 1, 5, 5, 5, 5);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00006'', N''Áo Hoodie unisex'', N''áo này đẹp lắm'', GETDATE(), GETDATE(), 1, 6, 6, 6, 6);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00007'', N''Áo Polo vải gân'', N''mua đi đừng ngại'', GETDATE(), GETDATE(), 1, 7, 7, 7, 7);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00008'', N''Áo dài tay'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 8, 8, 8, 8);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00009'', N''T-Shirt chạy bộ Care & Share'', N''mua đi đừng ngại'', GETDATE(), GETDATE(), 1, 9, 9, 9, 9);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00010'', N''Áo khoác chạy bộ Fast & Free'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 10, 10, 10,
        10);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00011'', N''Quần dài chạy bộ Fast & Free'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 1, 2, 3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00012'', N''T-Shirt chạy bộ Graphic Special'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 1, 2, 3,
        4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00013'', N''Shorts chạy bộ Coolmate Basics'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 1, 2, 3,
        4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00014'', N''T-Shirt chạy bộ Essential'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 1, 2, 3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00015'', N''Shorts chạy bộ Ultra'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 1, 2, 3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00016'', N''Set chạy bộ Fast & Free'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 1, 2, 3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00017'', N''Quần Long tights mặc trong'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 1, 2, 3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00018'', N''Shorts chạy bộ 2 lớp Essential'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 1, 2, 3,
        4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00019'', N''T-Shirt chạy bộ Basics'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 1, 2, 3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00020'', N''T-Shirt chạy bộ Advanced'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 1, 2, 3, 4);
INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES (''SP00021'', N''Áo Singlet chạy bộ Fast & Free'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 1, 2, 3,
        4);

--INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
--VALUES (''SP00022'', N''Áo Tanktop Thể Thao Nam Sát Nách Form Regular'', N''Áo Tanktop Thể Thao Nam Sát Nách Form Regular là một item có tính chất mỏng nhẹ, thoáng mát nên rất thích hợp cho các hoạt động thể thao. Mang form dáng rộng rãi, thoải mái kết hợp chất liệu bền đẹp, chống nhăn, chống khuẩn tốt. Thân trước được thiết kế trơn, phía sau được thêm vào họa tiết in cá tính, là chiếc áo thun đáp ứng đầy đủ các tiêu chí để tập luyện thể thao.'', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
--INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
--VALUES (''SP00023'', N''Áo Thun Thể Thao Nam Tay Ngắn Rã Vai Form Fitted'', N''Áo Thun Thể Thao Nam Tay Ngắn Rã Vai Form Fitted là một item có tính chất mỏng nhẹ, thoáng mát nên rất thích hợp cho các hoạt động thể thao. Mang form dáng rộng rãi, thoải mái kết hợp chất liệu bền đẹp, chống nhăn, chống khuẩn tốt. Thân trước được thiết kế trơn, phía sau được thêm vào họa tiết in cá tính, là chiếc áo thun đáp ứng đầy đủ các tiêu chí để tập luyện thể thao.'', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
--INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
--VALUES (''SP00024'', N''Áo Thun Thể Thao Nam Tay Ngắn Phối Màu Form Slim'', N''Áo Thun Tay Ngắn Nam Form Slim là sản phẩm nằm trong bộ sưu tập "Active Wear". Áo được sử dụng chất vải nhẹ, trượt nước, co giãn 4 chiều và được dệt kim nguyên vòng với kỹ thuật thoáng khí. Thiết kế không đường may tinh tế ôm vừa cơ thể, tay áo raglan kết hợp kiểu dệt gân ở hai bên sườn và trước ngực tôn lên vòng ngực, tạo cảm giác thon gọn người mặc.'', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
--INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
--VALUES (''SP00025'', N''Áo Hoodie Thể Thao Tay Ngắn Có Nón Form Regular'', N''Áo Hoodie Thể Thao Tay Ngắn Có Nón Form Regular với phong cách thiết kế phá cách, mới lạ với form rộng, tay ngắn, có nón giúp cho người mặc tự tin phối nhiều style trang phục khác nhau. Chất vải pha Nylon Spandex kết hợp với kiểu dáng cổ điển, suông thẳng, vừa vặn vào phần cơ thể mang đến sự nhẹ nhàng, thoáng mát và dễ chịu cho người mặc. Áo hoodie form regular không chỉ đem đến sự thoải mái khi hoạt động mà còn giữ ấm trong thời tiết tập luyện lạnh.'', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
--INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
--VALUES (''SP00026'', N''Áo Singlet chạy bộ Fast & Free'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
--INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
--VALUES (''SP00027'', N''Áo Singlet chạy bộ Fast & Free'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
--INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
--VALUES (''SP00028'', N''Áo Singlet chạy bộ Fast & Free'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
--INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
--VALUES (''SP00029'', N''Áo Singlet chạy bộ Fast & Free'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
--INSERT INTO Product (code, name, Description, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
--VALUES (''SP00030'', N''Áo Singlet chạy bộ Fast & Free'', N''mua đi nghèo lắm rồi'', GETDATE(), GETDATE(), 1, 1, 2,3, 4);
USE
Nova_Store_DB
SELECT *
FROM Product

SELECT *
FROM ProductDetail
    INSERT
INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('' CT00001 '', 12, 20000, 20000, GETDATE(), GETDATE(), 1, 1, 1, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00002'', 22, 45000, 45000, GETDATE(), GETDATE(), 1, 2, 2, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00003'', 30, 100000, 100000, GETDATE(), GETDATE(), 1, 3, 1, 2);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00004'', 40, 25000, 25000, GETDATE(), GETDATE(), 1, 4, 2, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00005'', 31, 60000, 60000, GETDATE(), GETDATE(), 1, 5, 3, 4);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00006'', 12, 200000, 200000, GETDATE(), GETDATE(), 1, 6, 5, 6);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00007'', 11, 230000, 230000, GETDATE(), GETDATE(), 1, 7, 7, 8);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00008'', 60, 40000, 40000, GETDATE(), GETDATE(), 1, 8, 9, 10);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00009'', 27, 55000, 55000, GETDATE(), GETDATE(), 1, 9, 2, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00010'', 38, 20000, 20000, GETDATE(), GETDATE(), 1, 10, 4, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00011'', 36, 12000, 12000, GETDATE(), GETDATE(), 1, 11, 4, 9);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00012'', 18, 22000, 22000, GETDATE(), GETDATE(), 1, 12, 2, 7);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00013'', 24, 44000, 44000, GETDATE(), GETDATE(), 1, 13, 3, 8);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00014'', 44, 56000, 56000, GETDATE(), GETDATE(), 1, 14, 10, 4);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00015'', 55, 62000, 62000, GETDATE(), GETDATE(), 1, 15, 6, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00016'', 46, 72000, 72000, GETDATE(), GETDATE(), 1, 16, 3, 5);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00017'', 71, 88000, 88000, GETDATE(), GETDATE(), 1, 17, 1, 4);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00018'', 28, 78000, 78000, GETDATE(), GETDATE(), 1, 18, 9, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00019'', 23, 98000, 98000, GETDATE(), GETDATE(), 1, 19, 10, 2);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00020'', 26, 230000, 230000, GETDATE(), GETDATE(), 1, 20, 2, 4);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00021'', 2, 340000, 340000, GETDATE(), GETDATE(), 1, 21, 5, 8);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00022'', 34, 20000, 20000, GETDATE(), GETDATE(), 1, 1, 2, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00023'', 51, 210000, 210000, GETDATE(), GETDATE(), 1, 2, 3, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00024'', 61, 65000, 65000, GETDATE(), GETDATE(), 1, 3, 2, 8);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00025'', 73, 56000, 56000, GETDATE(), GETDATE(), 1, 4, 3, 5);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00026'', 82, 23000, 23000, GETDATE(), GETDATE(), 1, 5, 2, 7);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00027'', 23, 31000, 31000, GETDATE(), GETDATE(), 1, 6, 4, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00028'', 73, 900000, 900000, GETDATE(), GETDATE(), 1, 7, 2, 2);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00029'', 28, 230000, 230000, GETDATE(), GETDATE(), 1, 8, 5, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00030'', 38, 48000, 48000, GETDATE(), GETDATE(), 1, 9, 3, 9);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00031'', 128, 20000, 20000, GETDATE(), GETDATE(), 1, 10, 2, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00032'', 38, 20000, 20000, GETDATE(), GETDATE(), 1, 11, 2, 9);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00033'', 282, 22000, 22000, GETDATE(), GETDATE(), 1, 12, 5, 7);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00034'', 382, 77000, 77000, GETDATE(), GETDATE(), 1, 13, 5, 4);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00035'', 311, 88000, 88000, GETDATE(), GETDATE(), 1, 14, 5, 5);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00036'', 111, 99000, 99000, GETDATE(), GETDATE(), 1, 15, 9, 7);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00037'', 112, 24000, 24000, GETDATE(), GETDATE(), 1, 16, 10, 10);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00038'', 138, 25000, 25000, GETDATE(), GETDATE(), 1, 17, 5, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00039'', 222, 67000, 67000, GETDATE(), GETDATE(), 1, 18, 6, 6);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00040'', 100, 45000, 45000, GETDATE(), GETDATE(), 1, 19, 2, 10);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00041'', 99, 78000, 78000, GETDATE(), GETDATE(), 1, 20, 1, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00042'', 28, 56000, 56000, GETDATE(), GETDATE(), 1, 21, 2, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00043'', 12, 43000, 43000, GETDATE(), GETDATE(), 1, 1, 1, 4);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00044'', 14, 78000, 78000, GETDATE(), GETDATE(), 1, 2, 2, 5);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00045'', 223, 30000, 30000, GETDATE(), GETDATE(), 1, 3, 9, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00046'', 333, 20000, 20000, GETDATE(), GETDATE(), 1, 4, 8, 3);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00047'', 492, 20000, 20000, GETDATE(), GETDATE(), 1, 5, 2, 1);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00048'', 389, 20000, 20000, GETDATE(), GETDATE(), 1, 6, 2, 4);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00049'', 123, 20000, 20000, GETDATE(), GETDATE(), 1, 7, 1, 2);
INSERT INTO ProductDetail (code, quantity, Price, PriceDiscount, CreateDate, UpdateDate, Status, ProductId, SizeId,
                           ColorId)
VALUES (''CT00050'', 324, 20000, 20000, GETDATE(), GETDATE(), 1, 8, 2, 3);


SELECT *
FROM Role
    INSERT
INTO Role (name, CreateDate, UpdateDate, Status)
VALUES ('' Admin '', GETDATE(), GETDATE(), 1);
INSERT INTO Role (name, CreateDate, UpdateDate, Status)
VALUES (''User'', GETDATE(), GETDATE(), 1);

SELECT *
FROM Account